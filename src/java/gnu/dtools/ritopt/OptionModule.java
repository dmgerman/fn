begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|gnu.dtools.ritopt
package|package
name|gnu
operator|.
name|dtools
operator|.
name|ritopt
package|;
end_package

begin_comment
comment|/**  * OptionModule.java  *  * Version:  *    $Id$  */
end_comment

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * This class is used as a repository for options. The Options class maintains  * an OptionModule repository for general options. The user may create option  * modules so that their options can overlap and be categorized. Option  * modules are invoked by specifying the option name delimited with square  * brackets.<p>  *  * For example, suppose we are writing a program called ServerManager  * that manages both an ftp and http server. One option that both a ftp  * and http kernel might have in common is the number of seconds before  * a request times out. Option modules are used to process two different  * values with the same option name. The shell command below demonstrates  * how two different modules are invoked.<p>  *<pre>  *  java ServerManager :http: --timeout=15 :ftp: --timeout=25  *</pre>  *  * Refer to the tutorial for more information on how to use option modules.  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|OptionModule
specifier|public
class|class
name|OptionModule
implements|implements
name|OptionRegistrar
block|{
comment|/**      * A repository of options registered with this module.      */
DECL|field|options
specifier|private
name|java
operator|.
name|util
operator|.
name|HashMap
name|options
decl_stmt|;
comment|/**      * The name of this module.      */
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
comment|/**      * Returns whether this module is deprecated.      */
DECL|field|deprecated
specifier|private
name|boolean
name|deprecated
decl_stmt|;
comment|/**      * The default short option.      */
DECL|field|DEFAULT_SHORT_OPTION
specifier|public
specifier|static
specifier|final
name|char
name|DEFAULT_SHORT_OPTION
init|=
literal|'\0'
decl_stmt|;
comment|/**      * The default long option.      */
DECL|field|DEFAULT_LONG_OPTION
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_LONG_OPTION
init|=
literal|null
decl_stmt|;
comment|/**      * The default description.      */
DECL|field|DEFAULT_DESCRIPTION
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_DESCRIPTION
init|=
literal|"No description given"
decl_stmt|;
comment|/**      * The default deprecation status.      */
DECL|field|DEFAULT_DEPRECATED
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_DEPRECATED
init|=
literal|false
decl_stmt|;
comment|/**      * The default module name.      */
DECL|field|DEFAULT_MODULE_NAME
specifier|public
specifier|static
specifier|final
name|String
name|DEFAULT_MODULE_NAME
init|=
literal|"Special"
decl_stmt|;
comment|/**      * Constructs an OptionModule with the default name.      */
DECL|method|OptionModule ()
specifier|public
name|OptionModule
parameter_list|()
block|{
name|this
argument_list|(
name|DEFAULT_MODULE_NAME
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs an OptionModule with the name passed.      *      * @param name  The name of the module.      */
DECL|method|OptionModule ( String name )
specifier|public
name|OptionModule
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|options
operator|=
operator|new
name|java
operator|.
name|util
operator|.
name|HashMap
argument_list|()
expr_stmt|;
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|deprecated
operator|=
literal|false
expr_stmt|;
block|}
comment|/**      * Register an option into the repository as a long option.      *      * @param longOption  The long option name.      * @param option      The option to register.      */
DECL|method|register ( String longOption, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|Option
name|option
parameter_list|)
block|{
name|register
argument_list|(
name|longOption
argument_list|,
name|DEFAULT_SHORT_OPTION
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
comment|/**      * Register an option into the repository as a short option.      *      * @param shortOption The short option name.      * @param option      The option to register.      */
DECL|method|register ( char shortOption, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|char
name|shortOption
parameter_list|,
name|Option
name|option
parameter_list|)
block|{
name|register
argument_list|(
name|DEFAULT_LONG_OPTION
argument_list|,
name|shortOption
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
comment|/**      * Register an option into the repository both as a short and long option.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param option      The option to register.      */
DECL|method|register ( String longOption, char shortOption, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|Option
name|option
parameter_list|)
block|{
name|register
argument_list|(
name|longOption
argument_list|,
name|shortOption
argument_list|,
name|DEFAULT_DESCRIPTION
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
comment|/**      * Register an option into the repository both as a short and long option.      * Initialize its description with the description passed.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param description The description of the option.      * @param option      The option to register.      */
DECL|method|register ( String longOption, char shortOption, String description, Option option )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|String
name|description
parameter_list|,
name|Option
name|option
parameter_list|)
block|{
name|register
argument_list|(
name|longOption
argument_list|,
name|shortOption
argument_list|,
name|description
argument_list|,
name|option
argument_list|,
name|DEFAULT_DEPRECATED
argument_list|)
expr_stmt|;
block|}
comment|/**      * Register an option into the repository both as a short and long option.      * Initialize its description with the description passed.      *      * @param longOption  The long option name.      * @param shortOption The short option name.      * @param description The description of the option.      * @param option      The option to register.      * @param deprecated  A boolean indicating whether an option should      *                    be deprecated.      */
DECL|method|register ( String longOption, char shortOption, String description, Option option, boolean deprecated )
specifier|public
name|void
name|register
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|,
name|String
name|description
parameter_list|,
name|Option
name|option
parameter_list|,
name|boolean
name|deprecated
parameter_list|)
block|{
if|if
condition|(
name|optionExists
argument_list|(
name|option
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|OptionRegistrationException
argument_list|(
literal|"Option Already Registered"
argument_list|,
name|option
argument_list|)
throw|;
block|}
name|option
operator|.
name|setLongOption
argument_list|(
name|longOption
argument_list|)
expr_stmt|;
name|option
operator|.
name|setShortOption
argument_list|(
name|shortOption
argument_list|)
expr_stmt|;
name|option
operator|.
name|setDeprecated
argument_list|(
name|deprecated
argument_list|)
expr_stmt|;
name|option
operator|.
name|setDescription
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|options
operator|.
name|put
argument_list|(
name|option
operator|.
name|getHashKey
argument_list|()
argument_list|,
name|option
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns whether the option exists in this module.      *      * @param option   The option to check for existance.      *      * @return A boolean value indicating whether the option passed exists.      */
DECL|method|optionExists ( Option option )
specifier|public
name|boolean
name|optionExists
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
return|return
name|optionExists
argument_list|(
name|option
operator|.
name|getShortOption
argument_list|()
argument_list|)
operator|||
name|optionExists
argument_list|(
name|option
operator|.
name|getLongOption
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns whether the option referred by a short option exists in this      * module.      *      * @param shortOption   The option to check for existance.      *      * @return A boolean value indicating whether the option passed exists.      */
DECL|method|optionExists ( char shortOption )
specifier|public
name|boolean
name|optionExists
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|char
name|c
init|=
name|next
operator|.
name|getShortOption
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|0
operator|&&
name|c
operator|==
name|shortOption
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Returns whether the option referred by a long option exists in this      * module.      *      * @param longOption   The option to check for existance.      *      * @return A boolean value indicating whether the option passed exists.      */
DECL|method|optionExists ( String longOption )
specifier|public
name|boolean
name|optionExists
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|s
init|=
name|next
operator|.
name|getLongOption
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
operator|&&
name|s
operator|.
name|equals
argument_list|(
name|longOption
argument_list|)
condition|)
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Return an iterator over the option repository contained in this module.      *      * @return An iterator over the repository.      */
DECL|method|getOptionIterator ()
specifier|public
name|Iterator
name|getOptionIterator
parameter_list|()
block|{
return|return
name|options
operator|.
name|values
argument_list|()
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|/**      * Returns the option referred by the long option passed.      *      * @param shortOption The option to retrieve.      *      * @return An option referred to by this module. null is returned      *         if it does not exist.      */
DECL|method|getOption ( char shortOption )
specifier|public
name|Option
name|getOption
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
name|Option
name|retval
init|=
literal|null
decl_stmt|;
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|char
name|c
init|=
name|next
operator|.
name|getShortOption
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|'\0'
operator|&&
name|c
operator|==
name|shortOption
condition|)
name|retval
operator|=
name|next
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
comment|/**      * Returns the option referred by the long option passed.      *      * @param longOption The option to retrieve.      *      * @return An option referred to by this module. null is returned      *         if it does not exist.      */
DECL|method|getOption ( String longOption )
specifier|public
name|Option
name|getOption
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
name|Option
name|retval
init|=
literal|null
decl_stmt|;
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|String
name|s
init|=
name|next
operator|.
name|getLongOption
argument_list|()
decl_stmt|;
if|if
condition|(
name|s
operator|!=
literal|null
operator|&&
name|s
operator|.
name|equals
argument_list|(
name|longOption
argument_list|)
condition|)
name|retval
operator|=
name|next
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
comment|/**      * Returns the help information as a String.      *      * @return The help information as a String.      */
DECL|method|getHelp ()
specifier|public
name|String
name|getHelp
parameter_list|()
block|{
name|String
name|retval
init|=
literal|""
decl_stmt|;
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|retval
operator|+=
name|next
operator|.
name|getHelp
argument_list|()
operator|+
literal|"\n"
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
comment|/**      * Writes the help information to a print stream.      *      * @param ps  The print stream to write to.      */
DECL|method|writeFileToPrintStream ( PrintStream ps )
specifier|public
name|void
name|writeFileToPrintStream
parameter_list|(
name|PrintStream
name|ps
parameter_list|)
block|{
name|Collection
name|col
init|=
name|options
operator|.
name|values
argument_list|()
decl_stmt|;
name|Iterator
name|it
init|=
name|col
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|ps
operator|.
name|println
argument_list|(
literal|":"
operator|+
name|name
operator|+
literal|":"
argument_list|)
expr_stmt|;
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Option
name|next
init|=
operator|(
name|Option
operator|)
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|ps
operator|.
name|println
argument_list|(
name|next
operator|.
name|getOptionFileLine
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Returns whether this module is deprecated.      *      * @return A boolean value indicating whether this module is deprecated.      */
DECL|method|isDeprecated ()
specifier|public
name|boolean
name|isDeprecated
parameter_list|()
block|{
return|return
name|deprecated
return|;
block|}
comment|/**      * Sets whether this module is deprecated.      *      * @param deprecated The new status.      */
DECL|method|setDeprecated ( boolean deprecated )
specifier|public
name|void
name|setDeprecated
parameter_list|(
name|boolean
name|deprecated
parameter_list|)
block|{
name|this
operator|.
name|deprecated
operator|=
name|deprecated
expr_stmt|;
block|}
comment|/**      * Called by the OptionsProcessor when an option in the target module      * is invoked.      *      * @param shortOption The option to invoke.      * @param text        The text to pass to the modifier.      */
DECL|method|action ( char shortOption, char text )
specifier|public
name|void
name|action
parameter_list|(
name|char
name|shortOption
parameter_list|,
name|char
name|text
parameter_list|)
block|{
name|action
argument_list|(
name|shortOption
argument_list|,
literal|""
operator|+
name|text
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called by the OptionsProcessor when an option in the target module      * is invoked.      *      * @param longOption The option to invoke.      * @param text       The text to pass to the modifier.      */
DECL|method|action ( String longOption, char text )
specifier|public
name|void
name|action
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|text
parameter_list|)
block|{
name|action
argument_list|(
name|longOption
argument_list|,
literal|""
operator|+
name|text
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called by the OptionsProcessor when an option in the target module      * is invoked.      *      * @param shortOption The option to invoke.      * @param text        The text to pass to the modifier.      */
DECL|method|action ( char shortOption, String text )
specifier|public
name|void
name|action
parameter_list|(
name|char
name|shortOption
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|Option
name|op
init|=
name|getOption
argument_list|(
name|shortOption
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
throw|throw
operator|new
name|OptionProcessingException
argument_list|(
literal|"Option -"
operator|+
name|shortOption
operator|+
literal|" does not"
operator|+
literal|" exist in module '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
throw|;
name|op
operator|.
name|setInvoked
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|op
operator|.
name|action
argument_list|()
expr_stmt|;
name|op
operator|.
name|modify
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
comment|/**      * Called by the OptionsProcessor when an option in the target module      * is invoked.      *      * @param longOption The option to invoke.      * @param text       The text to pass to the modifier.      */
DECL|method|action ( String longOption, String text )
specifier|public
name|void
name|action
parameter_list|(
name|String
name|longOption
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|Option
name|op
init|=
name|getOption
argument_list|(
name|longOption
argument_list|)
decl_stmt|;
if|if
condition|(
name|op
operator|==
literal|null
condition|)
throw|throw
operator|new
name|OptionProcessingException
argument_list|(
literal|"Option --"
operator|+
name|longOption
operator|+
literal|" does not"
operator|+
literal|" exist in module '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
throw|;
name|op
operator|.
name|setInvoked
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|op
operator|.
name|action
argument_list|()
expr_stmt|;
name|op
operator|.
name|modify
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
comment|/**      * Set the name of this module.      *      * @param name   The new name.      */
DECL|method|setName ( String name )
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
comment|/**      * Returns the name of this module.      *      * @return The name of this module.      */
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
block|}
end_class

begin_comment
comment|/** OptionModule **/
end_comment

end_unit

