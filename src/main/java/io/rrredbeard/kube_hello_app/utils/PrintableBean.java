package io.rrredbeard.kube_hello_app.utils;

import io.rrredbeard.kube_hello_app.KubeHelloApplication;

import javax.annotation.PostConstruct;

public interface PrintableBean {

	@PostConstruct
	default void logMe(){
		KubeHelloApplication.logConfig(this);
	}

}
