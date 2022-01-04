#!/bin/bash
sed "s/tagname/$1/g" pods.yml > ranking-app-pod.yml
