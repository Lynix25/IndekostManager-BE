package com.indekos.controller;

import com.indekos.common.helper.GlobalAcceptions;
import com.indekos.model.RoomPriceDetail;
import com.indekos.services.RoomDetailService;
import com.indekos.services.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ProdONLYController {

    @Autowired
    ServiceService serviceService;

    @Autowired
    RoomDetailService roomDetailService;

    @GetMapping("/unpaid/{id}")
    public ResponseEntity getAllPayableItem(@PathVariable String id){


        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/roomdetail/{id}")
    public ResponseEntity getRoomDetail(@PathVariable String id){
        RoomPriceDetail roomPriceDetail = roomDetailService.getByID(id);

        return GlobalAcceptions.data(roomPriceDetail, "Room Detail Data");
    }

//    @Autowired
//    ModelMapper modelMapper;
//    @PostMapping
//    public ResponseEntity test(@RequestBody ClassA request){
////        modelMapper.addMappings(new PropertyMap<ClassA, ClassB>() {
////            @Override
////            protected void configure() {
////                map().setNamaLengkap(Utils.UUID4());
////                map().setAlamat(source.getAddress());
////            }
////        });
//        modelMapper.addMappings(new PropertyMap<ClassA, ClassC>() {
//            @Override
//            protected void configure() {
//                new ClassC("System");
//                map().setNamaLengkap(Utils.UUID4() + "asdasd");
//                map().setAlamat(source.getAddress());
//            }
//        });
////        modelMapper.typeMap(ClassA.class, ClassB.class).addMappings(mapper -> {
////            mapper.map(src -> {
////                String resr = Utils.UUID4();
////                return resr;
////            }, ClassB::setNamaLengkap);
////            mapper.map(src -> src.getAddress(), ClassB::setAlamat);
////            mapper.map(ClassA::getName,ClassB::setNamaLengkap);
////        });
//
////        ClassC response = new ClassC("System");
//        ClassC response = modelMapper.map(request, ClassC.class);
////        modelMapper.map(request,response);
////        modelMapper.map(request,response);
//        modelMapper.validate();
//        return new ResponseEntity<>(response,HttpStatus.OK);
//    }

}
