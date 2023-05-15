## 🚩 Table of Contents

- [APM Utils](#-What is APM Utils?)
- [Supporting Frameworks](#-What frameworks does this support?)
- [Metric Details](#-What metrics it is publishing currently?)
- [Integration](#-How to integrate this to services?)
- [Error Reporting](#-Publishing errors to datadog)


### What is APM Utils?

This is sdk built to interact with our APM(Datadog, New relic, Appdyanmics etc.,)
If you want publish any connection pool metrics(redis, sql etc.,) or application thread pool metrics or any other
custome metrics you can use this library This library was bulit in a vision that you services should not care about APM
implemenatation of doing the things

### What frameworks does this support?
1. Dropwizard
2. SpringBoot

### What metrics it is publishing currently?

<details>

<summary><b>Database Connection Pool Metrics(SQL)</b></summary>

| Metric | Metric as per APM |
| --- | --- |
| MaxActiveConnections | \<serviceName>.db.maxActiveConnections |
| MinActiveConnections | \<serviceName>.db.minIdleConnections |
| ActiveConnections | \<serviceName>.db.activeConnections |
| CurrentConnections | \<serviceName>.db.currentConnection |
| WaitingConnections | \<serviceName>.db.waitingConnections |

</details>


<details>

<summary><b>Redis Connection Pool Metrics(Only DropWizard)</b></summary>

| Metric | Metric as per APM |
| --- | --- |
| MaxActiveConnections | \<serviceName>.redis.max |
| IdleConnections | \<serviceName>.redis.idle |
| MinActiveConnections | \<serviceName>.db.minIdleConnections |
| ActiveConnections | \<serviceName>.redis.active |
| CurrentConnections | \<serviceName>.db.currentConnection |
| WaitingConnections | \<serviceName>.redis.wait |

</details>

<details>

<summary><b>Application Thread Pool Metrics</b></summary>

| Metric | Metric as per APM |
| --- | --- |
| ThreadUtilization | \<serviceName>.threads.utilization |
| CurrentThreads | \<serviceName>.threads.size |
| MaxNumberOfThreads | \<serviceName>.threads.maxThreads |
| BusyThreads | \<serviceName>.threads.busy |
| WaitingJobsForThreads(**Not available for spring boot**) | \<serviceName>.threads.jobs |

</details>

### How to integrate this to services?

<details>
<summary>DropWizard Services</summary>
<details>
<summary>With Base Application Extended</summary>
1. Min mandatory version of utility module - 1.5.442.<br/>
2. Paste the following config in your yml file 

```yaml
serviceName: diffpromise
databaseConnectionPoolName: hibernate
apmConfiguration:
  apm: DATADOG
  datadogBO:
    apiKey: ----dcd9d2f61d02c7713fe4d8aeca70
    endPoint: https://api.datadoghq.com
  metricPublishingIntervalInSeconds: 60
  shouldPublishMetrics: true
  sqlConnectionPoolMonitoringRequired: false
  redisConnectionPoolMonitoringRequired: false
```
3. Description of each param for the above-mentioned config <br/>

| Property | Description |
| --- | --- |
| serviceName | ServiceName should be unique for each service. This is the serviceName which is mentioned in above metrics tables |
| databaseConnectionPoolName | 1.  "hibernate" for all the service which are using hibernate.<br/> 2.  If your service is not using hibernate pls check function "getConnectionPoolName" in JPA setup file  |
| apm.apiKey | Please reach out to devops for api key |
| metricPublishingIntervalInSeconds | This defines at what frequency we should publish metrics to APM |
| shouldPublishMetrics | This is to switch on and off metrics publishing. It is always **recommended to keep this is as false** when you are running service in local |
| sqlConnectionPoolMonitoringRequired | This is to switch on and off SQL Connection pool metrics publishing. It is always **recommended to keep this is as false** when you are running service in local |
| redisConnectionPoolMonitoringRequired | This is to switch on and off redis Connection pool metrics publishing. It is always **recommended to keep this is as false** when you are running service in local |


</details>
<details>
<summary>Applications that don't extend Base Application</summary>
In addition to the steps mentioned in (With Base Application Extended). Follow this one more step <br/>
1.  Paste the following code in your Application file **run** method

```java 
        APMUtils apmUtils = new APMUtils(configuration.getApmConfiguration().getApm(),configuration.getApmConfiguration().getDatadogBO());
        parentConfigWebApplicationContext.getBeanFactory().registerSingleton("apmUtils", apmUtils);
        
        DropwizardAPMMetricPublisher dropwizardAPMMetricPublisher = new DropwizardAPMMetricPublisher(configuration.getApmConfiguration().getApm(), configuration.getServiceName(), configuration.getApmConfiguration().getDatadogBO(), configuration.getMySqlConnectionPoolName(),
                configuration.getApmConfiguration().getMetricPublishingIntervalInSeconds(), environment.metrics(),
                MetricsSwitchConfiguration.builder().redisConnectionPoolMonitoringRequired(configuration.getApmConfiguration().getRedisConnectionPoolMonitoringRequired())
                        .shouldPublishMetrics(configuration.getApmConfiguration().getShouldPublishMetrics())
                        .sqlConnectionPoolMonitoringRequired(configuration.getApmConfiguration().getSqlConnectionPoolMonitoringRequired()).build());

        dropwizardAPMMetricPublisher.startReporting();
        //Paste the below 3 lines if your application uses redis
        environment.metrics().register("redis.connection.max",(Gauge) () ->configuration.getRedis().getMaxTotal());
        environment.metrics().register("redis.connection.maxBorrowWaitTimeMillis",(Gauge) () -> Math.toIntExact(jedisPool.getMaxBorrowWaitTimeMillis()));
        environment.metrics().register("redis.connection.meanBorrowWaitTimeMillis",(Gauge) () -> Math.toIntExact(jedisPool.getMeanBorrowWaitTimeMillis()));
        
        //Paste the below 2 lines if your application uses SQL

        environment.metrics().register("io.dropwizard.db.ManagedPooledDataSource." + configuration.getMySqlConnectionPoolName() + ".maxActive",(Gauge) () ->configuration.getDataSourceFactory().getMaxSize());
        environment.metrics().register("io.dropwizard.db.ManagedPooledDataSource." + configuration.getMySqlConnectionPoolName() + ".minIdleConnections",(Gauge) () ->configuration.getDataSourceFactory().getMinSize());

```
</details>
</details>
<details>
<summary>Spring Boot Services</summary>
WIP
</details>

### Publishing errors to datadog
```java
APMUtils.publishError(ex);
```
**Note:** 
1. For the service extending base application - use the latest utility module <br/>
2. Else paste the following code in  your Application file **run** method
```java
    APMUtils apmUtils = new APMUtils(configuration.getApmConfiguration().getApm(),configuration.getApmConfiguration().getDatadogBO());
    parentConfigWebApplicationContext.getBeanFactory().registerSingleton("apmUtils", apmUtils);
```
3. For Spring boot service paste the following code in Application file
```java
 @Bean
    public APMUtils apmUtils() {
        return new APMUtils(vendorGatewayServiceConfiguration.getApmConfiguration().getApm(), vendorGatewayServiceConfiguration.getApmConfiguration().getDatadogBO());
    }
```

