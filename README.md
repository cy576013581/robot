# robot
this is robot

#### 开发日志

- 解决天气预报获取乱码的bug，原因是接口数据是经过 GZIP 压缩过的，使用HttpClient 解决，其内置了对于 GZIP 的支持   2018-11-12