def test() {
   echo"I am Tarik, and I am here"
        sh "mvn package"
}

def building() {
   echo "I will build the project image"
   sh "docker build -t tarik3/jenkins-test ."
   
}
def running() {
  echo"I will run the image"
   sh "docker run tarik3/jenkins-test:${params.VERSION} "
        
    
}
return this
