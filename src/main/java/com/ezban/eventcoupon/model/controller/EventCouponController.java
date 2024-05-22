package com.ezban.eventcoupon.model.controller;

import com.ezban.eventcoupon.model.EventCoupon;
import com.ezban.eventcoupon.model.EventCouponRepository;
import com.ezban.eventcoupon.model.EventCouponService;
import com.ezban.host.model.Host;
import com.ezban.host.model.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/backstage/eventcoupon")
public class EventCouponController {

    @Autowired
    EventCouponService eventCouponSvc;

    @Autowired
    HostService hostSvc;

    @Autowired
    EventCouponRepository eventCouponRepository;

    @GetMapping("/select_page")
    public String selectPage() {
        return "backstage/eventcoupon/select_page";
    }

    @GetMapping("/listAllEventCoupon")
    public String listAllEventCoupon(Model model, Principal principal) {
        // 取得登入的活動主辦方
        Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();
        // 取得登入活動主辦方的優惠券
        List<EventCoupon> eventCoupons = eventCouponSvc.findByHostHostNo(host.getHostNo());
        model.addAttribute("eventCouponListData", eventCoupons);
        return "backstage/eventcoupon/listAllEventCoupon";
    }

    @GetMapping("/listOneEventCoupon")
    public String listOneEventCoupon(@RequestParam("eventCouponNo") int eventCouponNo, Model model, Principal principal) {
        // 取得登入的活動主辦方
        Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();
        // 取得登入活動主辦方的優惠券
        EventCoupon eventCoupon = eventCouponSvc.getOneEventCoupon(eventCouponNo);
        // 驗證優惠券是不是登入的主辦方的
        if (eventCoupon.getHost().getHostNo().equals(host.getHostNo())) {
            model.addAttribute("eventCoupon", eventCoupon);
        } else {
            model.addAttribute("errorMessage", "非您的優惠券無法查看");
        }
        return "backstage/eventcoupon/listOneEventCoupon";
    }

    @GetMapping("/update_eventCoupon_input")
    public String eventCouponUpdateEventCoupon() {
        return "backstage/eventcoupon/update_eventCoupon_input";
    }

    @GetMapping("/addEventCoupon")
    public String addEventCoupon(ModelMap model, Principal principal) {
        EventCoupon eventCoupon = new EventCoupon();
        Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();
        eventCoupon.setHost(host);
        model.addAttribute("eventCoupon", eventCoupon);
        model.addAttribute("host", host);
        return "backstage/eventcoupon/addEventCoupon";
    }

    /*
     * This method will be called on addEventCoupon.html form submission, handling POST request It also validates the user input
     */
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody EventCoupon eventCoupon, BindingResult result, Principal principal) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        if (result.hasErrors()) {
            // 驗證失敗，返回錯誤訊息
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "輸入格式錯誤。",
                    "errors", result.getAllErrors()
            ));
        }

        try {
            /*************************** 2.開始新增資料 *****************************************/
            Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();
            eventCoupon.setHost(host); // 設置主辦方資訊
            eventCouponSvc.addEventCoupon(eventCoupon);

            /*************************** 3.新增完成,準備轉交(Send the Success view) **************/
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "優惠券資料新增成功"
            ));
        } catch (Exception e) {
            /*************************** 處理新增過程中的異常 ************************************/
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "新增優惠券過程中出現錯誤",
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/getOne_For_Update")
    public String getOne_For_Update(@RequestParam("eventCouponNo") String eventCouponNo, ModelMap model, Principal principal) {
        /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
        /*************************** 2.開始查詢資料 *****************************************/
        EventCoupon eventCoupon = eventCouponSvc.getOneEventCoupon(Integer.valueOf(eventCouponNo));

        /*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
        // 獲取當前登入的主辦方並設置到EventCoupon對象中
        Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();
        eventCoupon.setHost(host); // 設置主辦方資訊

        model.addAttribute("eventCoupon", eventCoupon);
        return "backstage/eventcoupon/update_eventCoupon_input"; // 查詢完成後轉交update_eventCoupon_input.html
    }

    @PostMapping("/update")
    public String update(@RequestBody @Valid EventCoupon eventCoupon, BindingResult result, ModelMap model) {
        /*************************** 1. 驗證輸入格式 ************************/
        if (result.hasErrors()) {
            model.addAttribute("eventCoupon", eventCoupon);
            model.addAttribute("error", "輸入格式錯誤。");
            return "backstage/eventcoupon/update_eventCoupon_input";
        }

        try {
            /*************************** 2. 更新優惠券資料 ************************/
            eventCouponSvc.updateEventCoupon(eventCoupon);  // 執行更新操作

            /*************************** 3. 更新成功 ************************/
            model.addAttribute("success", "修改成功");
            model.addAttribute("eventCoupon", eventCouponSvc.getOneEventCoupon(eventCoupon.getEventCouponNo()));
            return "backstage/eventcoupon/listOneEventCoupon";
        } catch (Exception e) {
            /*************************** 4. 處理過程中發生錯誤，返回錯誤信息 ************************/
            model.addAttribute("eventCoupon", eventCoupon);
            model.addAttribute("error", "處理過程中發生錯誤：" + e.getMessage());
            return "backstage/eventcoupon/update_eventCoupon_input";
        }
    }

    @PostMapping("/getOne_For_Display")
    public String getOne_For_Display(
            /***************************1.接收請求參數 - 輸入格式的錯誤處理*************************/
            @NotEmpty(message = "優惠券代碼: 請勿空白")
            @RequestParam(value = "couponCode", required = true) String couponCode,
            ModelMap model, Principal principal) {

        /***************************2.開始查詢資料*********************************************/
        // 獲取當前登入的主辦方
        Host host = hostSvc.findHostByHostNo(principal.getName()).orElseThrow();

        // 查找優惠券
        Optional<EventCoupon> optionalEventCoupon = eventCouponSvc.getEventCouponByCouponCode(couponCode);

        if (optionalEventCoupon.isPresent()) {
            EventCoupon eventCoupon = optionalEventCoupon.get();
            // 驗證優惠券是否屬於當前登入的主辦方
            if (eventCoupon.getHost().getHostNo().equals(host.getHostNo())) {
                model.addAttribute("eventCoupon", eventCoupon);
                System.out.println("Found coupon: " + eventCoupon);
            } else {
                model.addAttribute("errorMessage", "<span class='error-message'>您沒有權限查看此優惠券。</span>");
                System.out.println("No permission to view this coupon.");
            }
        } else {
            model.addAttribute("errorMessage", "<span class='error-message'>未找到對應代碼的優惠券。</span>");
            System.out.println("Coupon not found.");
        }

        List<EventCoupon> list = eventCouponSvc.getAll();
        model.addAttribute("eventCouponListData", list); // for select_page.html

        /***************************3.查詢完成,準備轉交(Send the Success view)*****************/

        model.addAttribute("host", host);
        model.addAttribute("getOne_For_Display", true);

        return "backstage/eventcoupon/select_page";
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    //@ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder();
        for (ConstraintViolation<?> violation : violations) {
            strBuilder.append(violation.getMessage() + "<br>");
        }
        model.addAttribute("host", new Host());
        List<Host> list2 = hostSvc.getAll();
        model.addAttribute("hostListData", list2);

        String message = strBuilder.toString();
        return new ModelAndView("backstage/eventcoupon/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
    }

    //檢查是否存在優惠券，有的話折扣金額
    @GetMapping("/{couponCode}")
    public ResponseEntity<?> checkCouponCode(@PathVariable String couponCode) {
        Optional<EventCoupon> coupon = eventCouponRepository.findByCouponCode(couponCode);
        return coupon.map(eventCoupon -> ResponseEntity.ok(eventCoupon.getEventCouponDiscount()))
                .orElseGet(() -> ResponseEntity.badRequest().body(Integer.valueOf("Invalid coupon code")));
    }

    @ModelAttribute("hostListData")
    protected List<Host> referenceListData() {
        List<Host> list = hostSvc.getAll();
        return list;
    }

    @ModelAttribute("hostMapData")
    protected Map<Integer, String> referenceMapData() {
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "Host1");
        map.put(2, "Host2");
        map.put(3, "Host3");
        map.put(4, "Host4");
        map.put(5, "Host5");
        map.put(6, "Host6");
        map.put(7, "Host7");
        map.put(8, "Host8");
        map.put(9, "Host9");
        map.put(10, "Host10");
        return map;
    }

    @ModelAttribute("eventCouponListData")
    protected List<EventCoupon> referenceListData(Model model) {
        return eventCouponSvc.getAll();
    }
}
