package cn.qhb.haiv.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by qianying on 2018/7/30.
 */
@RestController(value = "IndexController")
@RequestMapping(value = "/index", produces = {"application/json;charset=UTF-8", "application/x-www-form-urlencoded","text/html;charset=UTF-8"})
public class IndexController {

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String error(){
        return "403";
    }

    /*public ModelAndView showJspHome(@PathVariable(value = "type") String type) {
        ModelAndView model = new ModelAndView();
        model.addObject("name", "帅帅111111");
        model.setViewName("home");
        return model;
    }*/
}
