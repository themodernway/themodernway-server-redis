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

package com.themodernway.server.redis.support

import com.themodernway.server.redis.support.spring.IRedisContext
import com.themodernway.server.redis.support.spring.IRedisDescriptor
import com.themodernway.server.redis.support.spring.IRedisProvider
import com.themodernway.server.redis.support.spring.RedisContextInstance

import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
public trait RedisGroovyTrait
{
    @Memoized
    public IRedisContext getRedisContext()
    {
        RedisContextInstance.getRedisContextInstance()
    }

    @Memoized
    public IRedisProvider getRedisProvider()
    {
        getRedisContext().getRedisProvider()
    }

    @Memoized
    public IRedisDescriptor getRedisDescriptor(String name)
    {
        getRedisProvider().getRedisDescriptor(name)
    }

    @Memoized
    public List<String> getRedisDescriptorNames()
    {
        getRedisProvider().getRedisDescriptorNames()
    }

    @Memoized
    public List<IRedisDescriptor> getRedisDescriptors()
    {
        getRedisProvider().getRedisDescriptors()
    }
}
