#!/usr/bin/env bash

curl --request GET -sL 										\
     --url "http://latest.hello-app.info/hello"				\
     --resolve "latest.hello-app.info:80:$( minikube ip )"
