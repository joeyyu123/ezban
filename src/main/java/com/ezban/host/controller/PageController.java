package com.ezban.host.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/hostlogin")
	public String showhostlogin() {
		return "backstage/HostLogin/hostlogin";
	}

	@GetMapping("/hostregister")
	public String showregisterPage() {
		return "backstage/HostLogin/hostregister";
	}

	@GetMapping("/passwordreset")
	public String showpasswordreset() {
		return "backstage/HostLogin/passwordreset";
	}

	@GetMapping("/hostpassrevise")
	public String showhostpassrevise() {
		return "backstage/HostLogin/hostpassrevise";
	}

	@GetMapping("/qaback")
	public String showqaback() {
		return "backstage/adminmanage/qaback";
	}

	@GetMapping("/qa")
	public String showqa() {
		return "frontstage/qa/qa";
	}

	@GetMapping("/productcomment")
	public String showproductcomment() {
		return "frontstage/commentreport/productcomment";
	}

	@GetMapping("/adminregister")
	public String showadminregister() {
		return "backstage/admin/adminregister";
	}

	@GetMapping("/adminlogin")
	public String showadminlogin() {
		return "backstage/admin/adminlogin";
	}

	@GetMapping("/adminpassrevise")
	public String showadminpassrevise() {
		return "backstage/admin/adminpassrevise";
	}

	@GetMapping("/adminpasswordreset")
	public String showadminpasswordreset() {
		return "backstage/admin/adminpasswordreset";
	}

	@GetMapping("/productreportback")
	public String showproductreportback() {
		return "backstage/adminmanage/productreportback";
	}

	@GetMapping("/admin")
	public String showadmin() {
		return "backstage/adminmanage/admin";
	}

	@GetMapping("/birthdayback")
	public String showbirthdayback() {
		return "backstage/adminmanage/birthdayback";
	}
	
	@GetMapping("/adminmanage")
	public String showadminmanage() {
		return "backstage/adminmanage/index";
	}
	@GetMapping("/eventreportback")
	public String showeventreportback() {
		return "backstage/adminmanage/eventreportback";
	}
	@GetMapping("/eventreport")
	public String showeventreport() {
		return "frontstage/commentreport/eventreport";
	}
}
