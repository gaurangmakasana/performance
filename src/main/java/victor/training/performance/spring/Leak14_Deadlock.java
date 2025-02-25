package victor.training.performance.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static victor.training.performance.util.PerformanceUtil.log;
import static victor.training.performance.util.PerformanceUtil.sleepq;

@RestController
@RequestMapping("leak14")
public class Leak14_Deadlock {

	// [RO] CATE DOI, CATE DOI ... : https://youtu.be/V798MhKfdZ8

	@GetMapping
	public String root() throws Exception {
		return "call <a href='./leak14/one'>/one</a> and <a href='./leak14/two'>/two</a> withing 3 secs..";
	}
	
	@GetMapping("/one")
	public String one() throws Exception {
		KillOne.entryPoint();
		return "--> You didn't call /two within the last 3 secs, didn't you?..";
	}
	
	@GetMapping("/two")
	public String two() throws Exception {
		KillTwo.entryPoint();
		return "--> You didn't call /one within the last 3 secs, didn't you?..";
	}
}



class KillOne {
	public static synchronized void entryPoint() {
		log("start One.a1()");
		sleepq(3_000);
		KillTwo.internalMethod();
		log("start One.a1()");
	}

	public static synchronized void internalMethod() {
		log("start One.b1()");
		sleepq(3_000);
		log("end One.b1()");
	}
}



class KillTwo {
	public static synchronized void entryPoint() {
		log("start Two.a2()");
		sleepq(3_000);
		KillOne.internalMethod();
		log("start Two.a2()");
	}
	public static synchronized void internalMethod() {
		log("start Two.b2()");
		sleepq(3_000);
		log("end Two.b2()");
	}
}
