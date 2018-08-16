package cn.qhb.haiv.controller.test;

import cn.qhb.haiv.model.access_control.ShiroToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.SecurityUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@Api(value = "ShiroTestController", description = "shiro测试接口", tags = "ShiroTest")
@RequestMapping(value = "/shiro", produces = "text/html;charset=UTF-8")
public class ShiroTestController {
    //跳转到登录表单页面

    @ApiOperation(value = "登录", notes = "登录"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @RequestMapping(value = "/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    /**
     * ajax登录请求
     *
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value = "登录", notes = "登录"/*,tags = "资产，添加"*/, httpMethod = "POST")
    @RequestMapping(value = "/ajaxLogin", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> submitLogin(@ApiParam(name = "username", type = "string", value = "用户名") String username, @ApiParam(name = "password", type = "string", value = "密码") String password, Model model) {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            ShiroToken token = new ShiroToken(username, password);
            SecurityUtils.getSubject().login(token);
            resultMap.put("status", 200);
            resultMap.put("message", "登录成功");
        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", e.getMessage());
        }
        return resultMap;
    }

    //跳转到主页
    @ApiOperation(value = "首页", notes = "首页"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    /**
     * 退出
     *
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> logout() {
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        try {
            //退出
            SecurityUtils.getSubject().logout();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return resultMap;
    }

    @ApiOperation(value = "添加", notes = "添加"/*,tags = "资产，添加"*/, httpMethod = "GET")
    @RequestMapping(value = "/add")
    public String add() {
        return "add";
    }
}
