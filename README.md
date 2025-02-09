### Notes to run

### Upload images to minikube
* Set the environment variables with `eval $(minikube docker-env)`
* Build the image with the Docker daemon of Minikube (e.g., `docker build -t ingess-tls-demo-app .`)
* Set the image in the pod specification like the build tag (e.g., my-image)
* Set the imagePullPolicy to Never, otherwise Kubernetes will try to download the image.
* Important note: You have to run `eval $(minikube docker-env)` on each terminal you want to use, since it only sets
  the environment variables for the current shell session.
* Unset environment variables `eval $(minikube docker-env -u)`


### Minikube
#### Run minikube with static IP, to be able for request to cluster IP
`minikube start --driver docker --static-ip 192.168.200.200`

#### Create pod and service 
* Apply manifest: `kubectl apply -f k8s-deployment.yml`
* Remove manifest: `kubectl delete -f k8s-deployment.yml`
* Forward service port: `kubectl port-forward service/main-app 8080:80`
#### Or by terminal commands
* Create pods `kubectl run main-app-by-command --image=ingess-tls-demo-app:latest --image-pull-policy=IfNotPresent --port=8080`
* Create service `kubectl expose pod main-app-by-command --port=80 --target-port=8080`
* Forward service port: `sudo kubectl port-forward service/main-app-by-command 80:80`

#### Create Ingress
* Check addons list (check if ingress enabled) `minikube addons list`
* Enable ingress `minikube addons enable ingress`
* Check ingress pods: `kubectl get pod -n ingress-nginx`
* Check ingress svc: `kubectl get svc -n ingress-nginx`
* Check ingress: `kubectl get ingress`
* Take info about ingress: `kubectl describe ingress <ingress_name>`

#### To add minikube ip to local hosts file:
* View: `cat /etc/hosts`
* Edit: `sudo vim /etc/hosts`

#### Create certificate
`openssl req -x509 -newkey rsa:4096 -sha256 -nodes -keyout tls.key -out tls.crt -subj "/CN=exmp-https.com" -days 365`
* `req` - request a certificate
* `x509` - type of certificate
* `newkey` - if there is no existing key, or could specify existing
* `rsa` - type of key
* `4096` - length of a key
* `sha256` - algorithm for creating a certificate
* `nodes` - no password protection for a key
* `keyout tls.key` - specify key name
* `out tls.crt` - specify certificate name
* `days 365` - expiration


* Create k8s secret `kubectl create secret tls exmp-https-tls --cert=tls.crt --key=tls.key`
* Check created secret `kubectl get secret -o yaml`
* Check result `curl --cacert tls.crt https://exmp-https.com` (if minikube not in docker)
* Check ingress controller pos name`kubectl -n ingress-nginx get pod`
* Forward ingress controller `kubectl -n ingress-nginx port-forward pod/ingress-nginx-controller-7c6974c4d8-sz5zn --address 0.0.0.0 80:80 443:443`
* Check website 'https://localhost'
* Or run `minikube ssh`, then add host from ingress manifest to minikube /etc/hosts file,
load cert inside minikube filesystem and after that,
use `curl --cacert tls.crt https://hostname` in minukube ssh session