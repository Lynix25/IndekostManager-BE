package com.indekos.services;

import com.indekos.common.helper.exception.InvalidRequestIdException;
import com.indekos.dto.RequestorDTO;
import com.indekos.dto.SimpleUserDTO;
import com.indekos.dto.TaskDTO;
import com.indekos.dto.TaskDetailDTO;
import com.indekos.dto.request.TaskCreateRequest;
import com.indekos.dto.request.TaskUpdateRequest;
import com.indekos.model.Task;
import com.indekos.model.User;
import com.indekos.repository.TaskRepository;
import com.indekos.utils.Constant;

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

    public TaskDTO getById(String id){
        try {
        	Task task = taskRepository.findById(id).get();
        	SimpleUserDTO user = userService.getUserInfoById(task.getCreatedBy());
        	
        	TaskDTO response = new TaskDTO();
        	response.setTask(task);
        	response.setUser(user);
        	
            return response;
        }catch (NoSuchElementException e){
            throw new InvalidRequestIdException("Invalid Task ID");
        }
    }

//    public List<Task> getAll(String requestor) {
//        return taskRepository.findAllByRequestor(requestor);
//    }
    
    public List<TaskDTO> getAll(String requestor) {
    	List<Task> tasks = taskRepository.findActiveTaskByRequestor(requestor);
    	List<TaskDTO> taskResponse = new ArrayList<>();
    	tasks.forEach(task -> {
    		taskResponse.add(getById(task.getId()));
    	});
    	
        return taskResponse;
    }
    
    public List<TaskDetailDTO> getAllCharged(String userId) {
    	List<Task> tasks = taskRepository.findActiveTaskByRequestor(userId);
    	List<TaskDetailDTO> taskResponse = new ArrayList<>();
    	tasks.forEach(task -> {
    		
    		if(task.getCharge() + task.getAdditionalCharge() > 0) {
    			
    			TaskDetailDTO taskDetail = new TaskDetailDTO();
    			
    			TaskDTO simpleTask = getById(task.getId());
    			taskDetail.setTask(simpleTask.getTask());
    			
    			RequestorDTO requestor = new RequestorDTO();
    			requestor.setName(simpleTask.getUser().getUserName());
    			requestor.setRoomId(simpleTask.getUser().getRoomId());
    			requestor.setRoomName(simpleTask.getUser().getRoomName());
    			taskDetail.setRequestor(requestor);
    			
    			com.indekos.model.Service service = serviceService.getByID(simpleTask.getTask().getServiceId());
    			taskDetail.setService(service);
    			
    			taskResponse.add(taskDetail);
    		}
    	});
    	
        return taskResponse;
    }

    private TaskDTO save(String modifierId, Task task){
        try {
            task.update(modifierId);

            SimpleUserDTO user = userService.getUserInfoById(task.getCreatedBy());
            TaskDTO response = new TaskDTO();
            response.setTask(taskRepository.save(task));            
            response.setUser(user);
            
            return response;
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

    public TaskDTO update(String id,TaskUpdateRequest request){
        Task task = getById(id).getTask();
        task.setStatus(request.getStatus());
        
        if(request.getNotes() != null)
        	task.setNotes(request.getNotes());
        
        if(request.getAdditionalCharge() != null)
        	task.setCharge(request.getAdditionalCharge());

        return save(request.getRequesterId(), task);
    }

    public TaskDTO register(TaskCreateRequest request){
        modelMapper.typeMap(TaskCreateRequest.class, Task.class).addMappings(mapper -> {
            mapper.map(TaskCreateRequest::getRequesterId, Task::create);
        });

        Task task = modelMapper.map(request, Task.class);
        task.setStatus(Constant.SUBMITTED);
        
        User user = userService.getById(request.getRequesterId());
        task.setUser(user);
        return save(request.getRequesterId(), task);
    }    
}