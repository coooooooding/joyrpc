package io.joyrpc.spring.boot.properties;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import io.joyrpc.config.AbstractIdConfig;
import io.joyrpc.spring.ConsumerBean;
import io.joyrpc.spring.ProviderBean;
import io.joyrpc.spring.RegistryBean;
import io.joyrpc.spring.ServerBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static io.joyrpc.spring.boot.properties.RpcProperties.PREFIX;

/*-
 * #%L
 * joyrpc
 * %%
 * Copyright (C) 2019 joyrpc.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

/**
 * @description:
 */
@ConfigurationProperties(prefix = PREFIX)
public class RpcProperties {

    public final static String PREFIX = "rpc";

    private Set<String> packages;

    private ServerBean server;

    private RegistryBean registry;

    private List<ConsumerBean> consumers;

    private List<ProviderBean> providers;

    private List<ServerBean> servers;

    private List<RegistryBean> registries;

    public Set<String> getPackages() {
        return packages;
    }

    public void setPackages(Set<String> packages) {
        this.packages = packages;
    }

    public ServerBean getServer() {
        return server;
    }

    public void setServer(ServerBean server) {
        this.server = server;
    }

    public RegistryBean getRegistry() {
        return registry;
    }

    public void setRegistry(RegistryBean registry) {
        this.registry = registry;
    }

    public List<ConsumerBean> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<ConsumerBean> consumers) {
        this.consumers = consumers;
    }

    public List<ProviderBean> getProviders() {
        return providers;
    }

    public void setProviders(List<ProviderBean> providers) {
        this.providers = providers;
    }

    public List<ServerBean> getServers() {
        return servers;
    }

    public void setServers(List<ServerBean> servers) {
        this.servers = servers;
    }

    public List<RegistryBean> getRegistries() {
        return registries;
    }

    public void setRegistries(List<RegistryBean> registries) {
        this.registries = registries;
    }

    public <T extends AbstractIdConfig> T getConfigBean(String name, Class<T> beanType, Supplier<T> supplier) {
        T config = getConfigBean(name, beanType);
        if (config == null) {
            config = supplier.get();
            config.setId(name);
        }
        return config;
    }

    public <T extends AbstractIdConfig> T getConfigBean(String name, Class<T> beanType) {
        if (beanType == ProviderBean.class) {
            return (T) getConfigBean(name, providers);
        } else if (beanType == ConsumerBean.class) {
            return (T) getConfigBean(name, consumers);
        } else if (beanType == ServerBean.class) {
            return (T) getConfigBean(name, servers);
        } else if (beanType == RegistryBean.class) {
            return (T) getConfigBean(name, registries);
        }
        return null;
    }

    protected <T extends AbstractIdConfig> T getConfigBean(String name, List<T> configs) {
        if (configs == null || configs.isEmpty()) {
            return null;
        }
        for (T cfg : configs) {
            if (name.equals(cfg.getId())) {
                return cfg;
            }
        }
        return null;
    }
}
