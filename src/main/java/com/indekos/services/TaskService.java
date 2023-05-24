package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.TaskDTO;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
import com.indekos.model.Notification;
import com.indekos.model.Task;
import com.indekos.model.User;
import com.indekos.repository.TaskRepository;
import com.indekos.utils.Constant;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskService {
	
    @Autowired
    ModelMapper modelMapper;
    
    @Autowired
    TaskRepository taskRepository;
    
    @Autowired
    ServiceService serviceService;
    
    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    RoleService roleService;

    public Task getById(String id){
        try {
        	Task task = taskRepository.findById(id).get();
            return task;
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Task ID");
        }
    }
    
    public List<TaskDTO> getAll(String requestor, String type) {
    	List<Task> tasks;
    	if(type.equalsIgnoreCase("ToDo")) tasks = taskRepository.findAllOrderByTarget(requestor);
        else if(type.equalsIgnoreCase("all")) tasks = taskRepository.findAllByRequestor(requestor);
    	else tasks = taskRepository.findActiveTaskByRequestor(requestor);

    	List<TaskDTO> taskDTOS = new ArrayList<>();
        for (Task task: tasks){
            TaskDTO taskDTO = new TaskDTO(task, task.getUser().getRoom());
            taskDTOS.add(taskDTO);
        }
        return taskDTOS;
    }
    
    public List<Task> getAllCharged(String userId) {
    	List<Task> tasks = taskRepository.findUnpaidTaskByRequestor(userId);
        return tasks;
    }

    private Task save(String modifierId, Task task){
        try {
            task.update(modifierId);
            return taskRepository.save(task);
        }
        catch (DataIntegrityViolationException e){
            System.out.println(e);
        }
        catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
		return null;
    }

    public Task save2(Task task){
        try{
            return taskRepository.save(task);
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException();
        }
    }
    public Task update(String id,TaskUpdateRequest request){
        Task task = getById(id);

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(TaskUpdateRequest.class, Task.class).addMappings(mapper -> {
            mapper.map(src -> src.getRequesterId(), Task::update);
            mapper.map(TaskUpdateRequest::getStatus, Task::setStatus);
        });

        modelMapper.map(TaskUpdateRequest.class, task);
        task.setStatus(request.getStatus());
        task.setNotes(request.getNotes());
        task.setCharge(task.getRequestedQuantity() * task.getService().getPrice());

        System.out.println(request.getStatus());
        if(request.getStatus().compareToIgnoreCase("Dalam Pengerjaan") == 0){
            Notification notification = new Notification("Info", "Task telah diterima", "Yay, task kamu akan dilaksakan ...", "/taskdetail.html?id="+id, false,task.getUser());
            notification.create(request.getRequesterId());
            notificationService.save(notification);
            notificationService.notif(notification);
        } else if (request.getStatus().compareToIgnoreCase("Selesai") == 0) {
            Notification notification = new Notification("Info", "Task telah selesai", "Permintaan layanan kamu sudah selesai, ...", "/taskdetail.html?id="+id, false,task.getUser());
            notification.create(request.getRequesterId());
            notificationService.save(notification);
            notificationService.notif(notification);
        }
        return save(request.getRequesterId(), task);
    }

    public Task register(TaskCreateRequest request){
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.typeMap(TaskCreateRequest.class, Task.class).addMappings(mapper -> {
            mapper.map(TaskCreateRequest::getRequesterId, Task::create);
        });

        Task task = modelMapper.map(request, Task.class);
        task.setStatus(Constant.SUBMITTED);

        com.indekos.model.Service service = serviceService.getByID(request.getServiceId());
        task.setService(service);
        
        User user = userService.getById(request.getRequesterId()).getUser();
        task.setUser(user);

        save(request.getRequesterId(), task);

        List<User> users = userService.getAllByRole(roleService.getByName("Admin"));
        users.addAll(userService.getAllByRole(roleService.getByName("Owner")));
        for(User u : users){
            Notification notification = new Notification("Task Baru 3","Task baru telah di request","Tenant ......", "/taskdetail.html?id="+task.getId(), false, u);
            notification.create("System");
            notificationService.save(notification);
            notificationService.notif(notification);
        };

//        task.setDueDate(System.currentTimeMillis() + (86400000 * service.getDueDate()));
        return task;
    }

    public List<Task> getManyById(List<String> ids){
        List<Task> tasks = new ArrayList<>();

        for (String id: ids) {
            Task task = getById2(id);
            tasks.add(task);
        }

        return tasks;
    }

    public Task getById2(String id){
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new InvalidRequestIdException("Task ID tidak valid : " + id));

        return task;
    }
}