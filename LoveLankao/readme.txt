将拿到的已经安装上的apk解压，在解压出的文件夹的META-INF文件夹下找到CERT.RSA,这里面包含了apk的签名信息，
在当前文件下下打开终端，执行
keytool -printcert –file ./CERT.RSA