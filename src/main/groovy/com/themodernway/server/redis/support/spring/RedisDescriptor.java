/*
 * Copyright (c) 2018, The Modern Way. All rights reserved.
 *
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
 */

package com.themodernway.server.redis.support.spring;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;

import com.themodernway.server.core.ICoreCommon;
import com.themodernway.server.core.logging.LoggingOps;

public class RedisDescriptor implements IRedisDescriptor, ICoreCommon, BeanNameAware, DisposableBean
{
    private final Logger        m_logger = LoggingOps.getLogger(getClass());

    private final AtomicBoolean m_isopen = new AtomicBoolean(false);

    private String              m_named;

    public void setName(final String named)
    {
        m_named = toTrimOrNull(named);
    }

    @Override
    public String getName()
    {
        return m_named;
    }

    @Override
    public void close() throws IOException
    {
        if (false == m_isopen.compareAndSet(true, false))
        {
            if (logger().isErrorEnabled())
            {
                logger().error(LoggingOps.THE_MODERN_WAY_MARKER, format("double close (%s).", getName()));
            }
            m_isopen.set(false);
        }
    }

    @Override
    public Logger logger()
    {
        return m_logger;
    }

    @Override
    public void setBeanName(final String named)
    {
        m_isopen.set(true);

        if (null == getName())
        {
            setName(getOriginalBeanName(named));
        }
    }

    @Override
    public boolean isOpen()
    {
        return m_isopen.get();
    }

    @Override
    public void destroy() throws Exception
    {
        if (isOpen())
        {
            close();
        }
    }
}
