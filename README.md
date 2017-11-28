#项目中遇到的坑

1、首先是配置web.xml文件时，在配置springmvc并指定其访问路径时，不得使用*.htm，这样会导致返回json数据时出现406错误；spring会使用`org.springframework.web.accept.ServletPathExtensionContentNegotiationStrategy`去处理请求而不是`MappingJacksonHttpMessageConverter `，这就导致返回的格式为`text/html`，所以会出现406错误。