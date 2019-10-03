begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// -*- Mode: Java; tab-width: 4; indent-tabs-mode: nil; c-basic-offset: 4 -*-
end_comment

begin_comment
comment|/*  * This file is part of the LibreOffice project.  *  * This Source Code Form is subject to the terms of the Mozilla Public  * License, v. 2.0. If a copy of the MPL was not distributed with this  * file, You can obtain one at http://mozilla.org/MPL/2.0/.  *  * This file incorporates work covered by the following license notice:  *  *   Licensed to the Apache Software Foundation (ASF) under one or more  *   contributor license agreements. See the NOTICE file distributed  *   with this work for additional information regarding copyright  *   ownership. The ASF licenses this file to you under the Apache  *   License, Version 2.0 (the "License"); you may not use this file  *   except in compliance with the License. You may obtain a copy of  *   the License at http://www.apache.org/licenses/LICENSE-2.0 .  */
end_comment

begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UnsupportedEncodingException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URLClassLoader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Random
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|bridge
operator|.
name|UnoUrlResolver
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|bridge
operator|.
name|XUnoUrlResolver
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|comp
operator|.
name|helper
operator|.
name|BootstrapException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|comp
operator|.
name|helper
operator|.
name|ComponentContext
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|comp
operator|.
name|helper
operator|.
name|ComponentContextEntry
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|comp
operator|.
name|loader
operator|.
name|JavaLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|comp
operator|.
name|servicemanager
operator|.
name|ServiceManager
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|container
operator|.
name|XSet
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|XInitialization
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|XMultiComponentFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lang
operator|.
name|XMultiServiceFactory
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|lib
operator|.
name|util
operator|.
name|NativeLibraryLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|loader
operator|.
name|XImplementationLoader
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|uno
operator|.
name|UnoRuntime
import|;
end_import

begin_import
import|import
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|uno
operator|.
name|XComponentContext
import|;
end_import

begin_comment
comment|/** Bootstrap offers functionality to obtain a context or simply     a service manager.     The service manager can create a few basic services, whose implementations  are:<ul><li>com.sun.star.comp.loader.JavaLoader</li><li>com.sun.star.comp.urlresolver.UrlResolver</li><li>com.sun.star.comp.bridgefactory.BridgeFactory</li><li>com.sun.star.comp.connections.Connector</li><li>com.sun.star.comp.connections.Acceptor</li><li>com.sun.star.comp.servicemanager.ServiceManager</li></ul>      Other services can be inserted into the service manager by     using its XSet interface:<pre>         XSet xSet = UnoRuntime.queryInterface( XSet.class, aMultiComponentFactory );         // insert the service manager         xSet.insert( aSingleComponentFactory );</pre> */
end_comment

begin_class
DECL|class|Bootstrap
specifier|public
class|class
name|Bootstrap
block|{
DECL|field|RANDOM_PIPE_NAME
specifier|private
specifier|static
specifier|final
name|Random
name|RANDOM_PIPE_NAME
init|=
operator|new
name|Random
argument_list|()
decl_stmt|;
DECL|field|M_LOADED_JUH
specifier|private
specifier|static
name|boolean
name|M_LOADED_JUH
init|=
literal|false
decl_stmt|;
DECL|method|insertBasicFactories (XSet xSet, XImplementationLoader xImpLoader)
specifier|private
specifier|static
name|void
name|insertBasicFactories
parameter_list|(
name|XSet
name|xSet
parameter_list|,
name|XImplementationLoader
name|xImpLoader
parameter_list|)
throws|throws
name|Exception
block|{
comment|// insert the factory of the loader
name|xSet
operator|.
name|insert
argument_list|(
name|xImpLoader
operator|.
name|activate
argument_list|(
literal|"com.sun.star.comp.loader.JavaLoader"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// insert the factory of the URLResolver
name|xSet
operator|.
name|insert
argument_list|(
name|xImpLoader
operator|.
name|activate
argument_list|(
literal|"com.sun.star.comp.urlresolver.UrlResolver"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// insert the bridgefactory
name|xSet
operator|.
name|insert
argument_list|(
name|xImpLoader
operator|.
name|activate
argument_list|(
literal|"com.sun.star.comp.bridgefactory.BridgeFactory"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// insert the connector
name|xSet
operator|.
name|insert
argument_list|(
name|xImpLoader
operator|.
name|activate
argument_list|(
literal|"com.sun.star.comp.connections.Connector"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
comment|// insert the acceptor
name|xSet
operator|.
name|insert
argument_list|(
name|xImpLoader
operator|.
name|activate
argument_list|(
literal|"com.sun.star.comp.connections.Acceptor"
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns an array of default commandline options to start bootstrapped      * instance of soffice with. You may use it in connection with bootstrap      * method for example like this:      *<pre>      *     List list = Arrays.asList( Bootstrap.getDefaultOptions() );      *     list.remove("--nologo");      *     list.remove("--nodefault");      *     list.add("--invisible");      *      *     Bootstrap.bootstrap( list.toArray( new String[list.size()] );      *</pre>      *      * @return an array of default commandline options      * @see #bootstrap( String[] )      * @since LibreOffice 5.1      */
DECL|method|getDefaultOptions ()
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|getDefaultOptions
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"--nologo"
block|,
literal|"--nodefault"
block|,
literal|"--norestore"
block|,
literal|"--nolockcheck"
block|}
return|;
block|}
comment|/**        backwards compatibility stub.         @param context_entries the hash table contains mappings of entry names (type string) to         context entries (type class ComponentContextEntry).         @throws Exception if things go awry.         @return a new context.      */
DECL|method|createInitialComponentContext (Hashtable<String, Object> context_entries)
specifier|public
specifier|static
name|XComponentContext
name|createInitialComponentContext
parameter_list|(
name|Hashtable
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|context_entries
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|createInitialComponentContext
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
name|context_entries
argument_list|)
return|;
block|}
comment|/** Bootstraps an initial component context with service manager and basic         jurt components inserted.         @param context_entries the hash table contains mappings of entry names (type string) to         context entries (type class ComponentContextEntry).         @throws Exception if things go awry.         @return a new context.     */
DECL|method|createInitialComponentContext (Map<String, Object> context_entries)
specifier|public
specifier|static
name|XComponentContext
name|createInitialComponentContext
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|context_entries
parameter_list|)
throws|throws
name|Exception
block|{
name|ServiceManager
name|xSMgr
init|=
operator|new
name|ServiceManager
argument_list|()
decl_stmt|;
name|XImplementationLoader
name|xImpLoader
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XImplementationLoader
operator|.
name|class
argument_list|,
operator|new
name|JavaLoader
argument_list|()
argument_list|)
decl_stmt|;
name|XInitialization
name|xInit
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XInitialization
operator|.
name|class
argument_list|,
name|xImpLoader
argument_list|)
decl_stmt|;
name|Object
index|[]
name|args
init|=
operator|new
name|Object
index|[]
block|{
name|xSMgr
block|}
decl_stmt|;
name|xInit
operator|.
name|initialize
argument_list|(
name|args
argument_list|)
expr_stmt|;
comment|// initial component context
if|if
condition|(
name|context_entries
operator|==
literal|null
condition|)
block|{
name|context_entries
operator|=
operator|new
name|HashMap
argument_list|<>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|// add smgr
name|context_entries
operator|.
name|put
argument_list|(
literal|"/singletons/com.sun.star.lang.theServiceManager"
argument_list|,
operator|new
name|ComponentContextEntry
argument_list|(
literal|null
argument_list|,
name|xSMgr
argument_list|)
argument_list|)
expr_stmt|;
comment|// ... xxx todo: add standard entries
name|XComponentContext
name|xContext
init|=
operator|new
name|ComponentContext
argument_list|(
name|context_entries
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|xSMgr
operator|.
name|setDefaultContext
argument_list|(
name|xContext
argument_list|)
expr_stmt|;
name|XSet
name|xSet
init|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XSet
operator|.
name|class
argument_list|,
name|xSMgr
argument_list|)
decl_stmt|;
comment|// insert basic jurt factories
name|insertBasicFactories
argument_list|(
name|xSet
argument_list|,
name|xImpLoader
argument_list|)
expr_stmt|;
return|return
name|xContext
return|;
block|}
comment|/**      * Bootstraps a servicemanager with the jurt base components registered.      *      * See also UNOIDL<code>com.sun.star.lang.ServiceManager</code>.      *      * @throws Exception if things go awry.      * @return     a freshly bootstrapped service manager      */
DECL|method|createSimpleServiceManager ()
specifier|public
specifier|static
name|XMultiServiceFactory
name|createSimpleServiceManager
parameter_list|()
throws|throws
name|Exception
block|{
return|return
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XMultiServiceFactory
operator|.
name|class
argument_list|,
name|createInitialComponentContext
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
literal|null
argument_list|)
operator|.
name|getServiceManager
argument_list|()
argument_list|)
return|;
block|}
comment|/** Bootstraps the initial component context from a native UNO installation.          @throws Exception if things go awry.         @return a freshly bootstrapped component context.          See also<code>cppuhelper/defaultBootstrap_InitialComponentContext()</code>.     */
DECL|method|defaultBootstrap_InitialComponentContext ()
specifier|public
specifier|static
specifier|final
name|XComponentContext
name|defaultBootstrap_InitialComponentContext
parameter_list|()
throws|throws
name|Exception
block|{
return|return
name|defaultBootstrap_InitialComponentContext
argument_list|(
operator|(
name|String
operator|)
literal|null
argument_list|,
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
operator|)
literal|null
argument_list|)
return|;
block|}
comment|/**      * Backwards compatibility stub.      *      * @param ini_file      *        ini_file (may be null: uno.rc besides cppuhelper lib)      * @param bootstrap_parameters      *        bootstrap parameters (maybe null)      *      * @throws Exception if things go awry.      * @return a freshly bootstrapped component context.      */
DECL|method|defaultBootstrap_InitialComponentContext (String ini_file, Hashtable<String, String> bootstrap_parameters)
specifier|public
specifier|static
specifier|final
name|XComponentContext
name|defaultBootstrap_InitialComponentContext
parameter_list|(
name|String
name|ini_file
parameter_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bootstrap_parameters
parameter_list|)
throws|throws
name|Exception
block|{
return|return
name|defaultBootstrap_InitialComponentContext
argument_list|(
name|ini_file
argument_list|,
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
operator|)
name|bootstrap_parameters
argument_list|)
return|;
block|}
comment|/** Bootstraps the initial component context from a native UNO installation.          See also<code>cppuhelper/defaultBootstrap_InitialComponentContext()</code>.          @param ini_file                ini_file (may be null: uno.rc besides cppuhelper lib)         @param bootstrap_parameters                bootstrap parameters (maybe null)          @throws Exception if things go awry.         @return a freshly bootstrapped component context.     */
DECL|method|defaultBootstrap_InitialComponentContext (String ini_file, Map<String, String> bootstrap_parameters)
specifier|public
specifier|static
specifier|final
name|XComponentContext
name|defaultBootstrap_InitialComponentContext
parameter_list|(
name|String
name|ini_file
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bootstrap_parameters
parameter_list|)
throws|throws
name|Exception
block|{
comment|// jni convenience: easier to iterate over array than calling Hashtable
name|String
name|pairs
index|[]
init|=
literal|null
decl_stmt|;
if|if
condition|(
literal|null
operator|!=
name|bootstrap_parameters
condition|)
block|{
name|pairs
operator|=
operator|new
name|String
index|[
literal|2
operator|*
name|bootstrap_parameters
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
name|int
name|n
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|bootstrap_parameter
range|:
name|bootstrap_parameters
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|pairs
index|[
name|n
operator|++
index|]
operator|=
name|bootstrap_parameter
operator|.
name|getKey
argument_list|()
expr_stmt|;
name|pairs
index|[
name|n
operator|++
index|]
operator|=
name|bootstrap_parameter
operator|.
name|getValue
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
operator|!
name|M_LOADED_JUH
condition|)
block|{
if|if
condition|(
literal|"The Android Project"
operator|.
name|equals
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"java.vendor"
argument_list|)
argument_list|)
condition|)
block|{
comment|// Find out if we are configured with DISABLE_DYNLOADING or
comment|// not. Try to load the lo-bootstrap shared library which
comment|// won't exist in the DISABLE_DYNLOADING case. (And which will
comment|// be already loaded otherwise, so nothing unexpected happens
comment|// that case.) Yeah, this would be simpler if I just could be
comment|// bothered to keep a separate branch for DISABLE_DYNLOADING
comment|// on Android, merging in master periodically, until I know
comment|// for sure whether it is what I want, or not.
name|boolean
name|disable_dynloading
init|=
literal|false
decl_stmt|;
try|try
block|{
name|System
operator|.
name|loadLibrary
argument_list|(
literal|"lo-bootstrap"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsatisfiedLinkError
name|e
parameter_list|)
block|{
name|disable_dynloading
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|disable_dynloading
condition|)
block|{
name|NativeLibraryLoader
operator|.
name|loadLibrary
argument_list|(
name|Bootstrap
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
literal|"juh"
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|NativeLibraryLoader
operator|.
name|loadLibrary
argument_list|(
name|Bootstrap
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|,
literal|"juh"
argument_list|)
expr_stmt|;
block|}
name|M_LOADED_JUH
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XComponentContext
operator|.
name|class
argument_list|,
name|cppuhelper_bootstrap
argument_list|(
name|ini_file
argument_list|,
name|pairs
argument_list|,
name|Bootstrap
operator|.
name|class
operator|.
name|getClassLoader
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
DECL|method|cppuhelper_bootstrap (String ini_file, String bootstrap_parameters[], ClassLoader loader)
specifier|private
specifier|static
specifier|native
name|Object
name|cppuhelper_bootstrap
parameter_list|(
name|String
name|ini_file
parameter_list|,
name|String
name|bootstrap_parameters
index|[]
parameter_list|,
name|ClassLoader
name|loader
parameter_list|)
throws|throws
name|Exception
function_decl|;
comment|/**      * Bootstraps the component context from a UNO installation.      *      * @throws BootstrapException if things go awry.      *      * @return a bootstrapped component context.      *      * @since UDK 3.1.0      */
DECL|method|bootstrap (URLClassLoader loader)
specifier|public
specifier|static
specifier|final
name|XComponentContext
name|bootstrap
parameter_list|(
name|URLClassLoader
name|loader
parameter_list|)
throws|throws
name|BootstrapException
block|{
name|String
index|[]
name|defaultArgArray
init|=
name|getDefaultOptions
argument_list|()
decl_stmt|;
return|return
name|bootstrap
argument_list|(
name|defaultArgArray
argument_list|,
name|loader
argument_list|)
return|;
block|}
comment|/**      * Bootstraps the component context from a UNO installation.      *      * @param argArray      *        an array of strings - commandline options to start instance of      *        soffice with      * @see #getDefaultOptions()      *      * @throws BootstrapException if things go awry.      *      * @return a bootstrapped component context.      *      * @since LibreOffice 5.1      */
DECL|method|bootstrap (String[] argArray, URLClassLoader loader)
specifier|public
specifier|static
specifier|final
name|XComponentContext
name|bootstrap
parameter_list|(
name|String
index|[]
name|argArray
parameter_list|,
name|URLClassLoader
name|loader
parameter_list|)
throws|throws
name|BootstrapException
block|{
name|XComponentContext
name|xContext
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// create default local component context
name|XComponentContext
name|xLocalContext
init|=
name|createInitialComponentContext
argument_list|(
operator|(
name|Map
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
operator|)
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|xLocalContext
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
literal|"no local component context!"
argument_list|)
throw|;
block|}
comment|// find office executable relative to this class's class loader
name|String
name|sOffice
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
operator|.
name|startsWith
argument_list|(
literal|"Windows"
argument_list|)
condition|?
literal|"soffice.exe"
else|:
literal|"soffice"
decl_stmt|;
name|File
name|fOffice
init|=
name|NativeLibraryLoader
operator|.
name|getResource
argument_list|(
name|loader
argument_list|,
name|sOffice
argument_list|)
decl_stmt|;
if|if
condition|(
name|fOffice
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
literal|"no office executable found!"
argument_list|)
throw|;
block|}
comment|// create call with arguments
comment|//We need a socket, pipe does not work. https://api.libreoffice.org/examples/examples.html
name|String
index|[]
name|cmdArray
init|=
operator|new
name|String
index|[
name|argArray
operator|.
name|length
operator|+
literal|2
index|]
decl_stmt|;
name|cmdArray
index|[
literal|0
index|]
operator|=
name|fOffice
operator|.
name|getPath
argument_list|()
expr_stmt|;
name|cmdArray
index|[
literal|1
index|]
operator|=
operator|(
literal|"--accept=socket,host=localhost,port=2083"
operator|+
literal|";urp;"
operator|)
expr_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|argArray
argument_list|,
literal|0
argument_list|,
name|cmdArray
argument_list|,
literal|2
argument_list|,
name|argArray
operator|.
name|length
argument_list|)
expr_stmt|;
comment|// start office process
name|Process
name|p
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmdArray
argument_list|)
decl_stmt|;
name|pipe
argument_list|(
name|p
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|System
operator|.
name|out
argument_list|,
literal|"CO> "
argument_list|)
expr_stmt|;
name|pipe
argument_list|(
name|p
operator|.
name|getErrorStream
argument_list|()
argument_list|,
name|System
operator|.
name|err
argument_list|,
literal|"CE> "
argument_list|)
expr_stmt|;
comment|// initial service manager
name|XMultiComponentFactory
name|xLocalServiceManager
init|=
name|xLocalContext
operator|.
name|getServiceManager
argument_list|()
decl_stmt|;
if|if
condition|(
name|xLocalServiceManager
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
literal|"no initial service manager!"
argument_list|)
throw|;
block|}
comment|// create a URL resolver
name|XUnoUrlResolver
name|xUrlResolver
init|=
name|UnoUrlResolver
operator|.
name|create
argument_list|(
name|xLocalContext
argument_list|)
decl_stmt|;
comment|// connection string
name|String
name|sConnect
init|=
literal|"uno:socket,host=localhost,port=2083"
operator|+
literal|";urp;StarOffice.ComponentContext"
decl_stmt|;
comment|// wait until office is started
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
condition|;
operator|++
name|i
control|)
block|{
try|try
block|{
comment|// try to connect to office
name|Object
name|context
init|=
name|xUrlResolver
operator|.
name|resolve
argument_list|(
name|sConnect
argument_list|)
decl_stmt|;
name|xContext
operator|=
name|UnoRuntime
operator|.
name|queryInterface
argument_list|(
name|XComponentContext
operator|.
name|class
argument_list|,
name|context
argument_list|)
expr_stmt|;
if|if
condition|(
name|xContext
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
literal|"no component context!"
argument_list|)
throw|;
block|}
break|break;
block|}
catch|catch
parameter_list|(
name|com
operator|.
name|sun
operator|.
name|star
operator|.
name|connection
operator|.
name|NoConnectException
name|ex
parameter_list|)
block|{
comment|// Wait 500 ms, then try to connect again, but do not wait
comment|// longer than 5 min (= 600 * 500 ms) total:
if|if
condition|(
name|i
operator|==
literal|600
condition|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
name|ex
argument_list|)
throw|;
block|}
name|Thread
operator|.
name|sleep
argument_list|(
literal|500
argument_list|)
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|BootstrapException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|RuntimeException
name|e
parameter_list|)
block|{
throw|throw
name|e
throw|;
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|lang
operator|.
name|Exception
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|BootstrapException
argument_list|(
name|e
argument_list|)
throw|;
block|}
return|return
name|xContext
return|;
block|}
DECL|method|pipe (final InputStream in, final PrintStream out, final String prefix)
specifier|private
specifier|static
name|void
name|pipe
parameter_list|(
specifier|final
name|InputStream
name|in
parameter_list|,
specifier|final
name|PrintStream
name|out
parameter_list|,
specifier|final
name|String
name|prefix
parameter_list|)
block|{
operator|new
name|Thread
argument_list|(
literal|"Pipe: "
operator|+
name|prefix
argument_list|)
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|BufferedReader
name|r
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|in
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
init|;
condition|;
control|)
block|{
name|String
name|s
init|=
name|r
operator|.
name|readLine
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|==
literal|null
condition|)
block|{
break|break;
block|}
name|out
operator|.
name|println
argument_list|(
name|prefix
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|(
name|System
operator|.
name|err
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|java
operator|.
name|io
operator|.
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|(
name|System
operator|.
name|err
argument_list|)
expr_stmt|;
block|}
block|}
block|}
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
end_class

begin_comment
comment|// vim:set shiftwidth=4 softtabstop=4 expandtab:
end_comment

end_unit
