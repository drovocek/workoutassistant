package ru.soft.webclient.resources;


//import org.springframework.cloud.openfeign.FeignClient;
//
//@FeignClient(name = "planner-users")//, fallback = UserFeignClientFallback.class)
public interface UserFeignClient {

//    @PostMapping("/user/id")
//    ResponseEntity<User> findUserById(@RequestBody Long id);
}

//@Component
//class UserFeignClientFallback implements UserFeignClient {
//
//    // этот метод будет вызываться, если сервис /user/id не будет доступен
//    @Override
//    public ResponseEntity<User> findUserById(Long id) {
//        return null;
//    }
//}