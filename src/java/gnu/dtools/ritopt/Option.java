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
comment|/**  * Option.java  *  * Version  *    $Id$  */
end_comment

begin_comment
comment|/**  * This is the principal base class for all Option classes. It contains  * constructors for short and long option initialization, utility members  * for help reporting and file writing, and deprecation facilities.<p>  *  * Options that provide array support should inherit from the ArrayOption  * class, and follow the guidelines defined both in the Option and  * ArrayOption class descriptions.<p>  *  * Non-abstract subclasses should implement the modify method. When an option  * is invoked, the value of the option is passed to the modify method.<p>  *  * Subclasses should provide several constructors so that registration is  * simple and uniform. Recommended constructors include a default constructor,  * an interface for initialization of short and long options,  * and one that allows both short and long option fields to be  * initialized. If the subclass implementation provides constructors which  * initialize its members then the member parameters must be before  * the short and long option initialization parameters.<p>  *  * Event driven option processing is provided in the NotifyOption class. In  * order to use a NotifyOption, the recipient object must implement the  * OptionListener class. Although it is not required, subclass implementations  * of NotifyOption should implement the OptionNotifier interface.<p>  *  * By default, the Option class considers the width of an output device  * to be eighty characters. It initializes the width of the help fields  * based on this figure. If a field exceeds its field width, it is  * truncated. The width constraints can be changed by invoking the appropriate  * static mutators.<p>  *  * Similar to the help reporting facilities, the same constraints are placed  * on the listing of options provided by the built-in menu interface. These  * constraints can be modified by executing the appropriate static mutators.  *<p>  *  * The Option class provides a facility for writing options files.  * For option file writing, there are only two field width constraints; the  * assignment and the comment.  *<pre>  * Assignment:                           Comment:  * --longOrShortOption=optionValue       ;description goes here [d]  *</pre>  * As shown above, an assignment includes the long or short option text,  * an equal sign, and the option's value. The comment includes the  * description, and "[d]" if the option is deprecated.<p>  *  * If the assignment exceeds its field width, the comment is placed before  * the assignment on a separate line. The comment is truncated if it  * exceeds eighty characters when it is placed before the assignment.  * However, if the assignment does not exceeds its field width and the comment  * does, the comment is truncated, and continued on the next line at the  * columnar position defined by the assignment's field width. Field widths  * may be modified by invoking the appropriate static mutator.<p>  *  * This class also provides a facility for deprecating options. An option is  * deprecated to discourage its use without removing the functionality it  * provides. An option is deprecated by invoking the deprecate method.  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|Option
specifier|public
specifier|abstract
class|class
name|Option
implements|implements
name|OptionModifiable
block|{
comment|/**      * The default width of the option field when the help usage is displayed.      */
DECL|field|DEFAULT_HELP_OPTION_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_HELP_OPTION_SIZE
init|=
literal|22
decl_stmt|;
comment|/**      * The default width of the type name field when the help usage is      * display.      */
DECL|field|DEFAULT_HELP_TYPENAME_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_HELP_TYPENAME_SIZE
init|=
literal|10
decl_stmt|;
comment|/**      * The default width of the description when the help usage is displayed.      */
DECL|field|DEFAULT_HELP_DESCRIPTION_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_HELP_DESCRIPTION_SIZE
init|=
literal|48
decl_stmt|;
comment|/**      * The default width of the deprecated field when the help usage is      * displayed.      */
DECL|field|DEFAULT_HELP_DEPRECATED_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_HELP_DEPRECATED_SIZE
init|=
literal|3
decl_stmt|;
comment|/**      * The default width of the option field when the menu usage is displayed.      */
DECL|field|DEFAULT_MENU_OPTION_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_MENU_OPTION_SIZE
init|=
literal|15
decl_stmt|;
comment|/**      * The default width of the type name field when the menu usage is      * displayed.      */
DECL|field|DEFAULT_MENU_TYPENAME_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_MENU_TYPENAME_SIZE
init|=
literal|10
decl_stmt|;
comment|/**      * The default width of the description field when the menu usage is      * displayed.      */
DECL|field|DEFAULT_MENU_DESCRIPTION_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_MENU_DESCRIPTION_SIZE
init|=
literal|48
decl_stmt|;
comment|/**      * The default width of the deprecated field when the menu usage is      * displayed.      */
DECL|field|DEFAULT_MENU_DEPRECATED_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_MENU_DEPRECATED_SIZE
init|=
literal|3
decl_stmt|;
comment|/**      * The default width of the option assignment in an option file.      */
DECL|field|DEFAULT_FILE_COMPLETE_OPTION_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_FILE_COMPLETE_OPTION_SIZE
init|=
literal|60
decl_stmt|;
comment|/**      * The default width of the comment in an option file. If the option      * and the comment exceeds the width of the device, the comment is      * truncated to the next line at the same columnar position of the      * previous comment line. If the option assignment line is longer than      * the width, the comment line is put before the option assignment it      * refers.      */
DECL|field|DEFAULT_FILE_COMMENT_SIZE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_FILE_COMMENT_SIZE
init|=
literal|16
decl_stmt|;
comment|/**      * The String holding the value of the long option. If there is no      * long option, this value is set to null.      */
DECL|field|longOption
specifier|private
name|String
name|longOption
decl_stmt|;
comment|/**      * The character holding the value of the short option. If there is no      * short option,t his value is set to '\0'.      */
DECL|field|shortOption
specifier|private
name|char
name|shortOption
decl_stmt|;
comment|/**      * The String holding the description of this option.      */
DECL|field|description
specifier|private
name|String
name|description
decl_stmt|;
comment|/**      * A flag identifying whether this option is deprecated.      */
DECL|field|deprecated
specifier|private
name|boolean
name|deprecated
decl_stmt|;
comment|/**      * The field width for the option specification that is reporter for      * help.      */
DECL|field|helpOptionSpecificationSize
specifier|private
specifier|static
name|int
name|helpOptionSpecificationSize
init|=
name|DEFAULT_HELP_OPTION_SIZE
decl_stmt|;
comment|/**      * The field width for the type name that is reported for help.      */
DECL|field|helpTypenameSize
specifier|private
specifier|static
name|int
name|helpTypenameSize
init|=
name|DEFAULT_HELP_TYPENAME_SIZE
decl_stmt|;
comment|/**      * The field width for the description that is reported during help.      */
DECL|field|helpDescriptionSize
specifier|private
specifier|static
name|int
name|helpDescriptionSize
init|=
name|DEFAULT_HELP_DESCRIPTION_SIZE
decl_stmt|;
comment|/**      * The field width for the deprecated flag that is reported during      * help.      */
DECL|field|helpDeprecatedSize
specifier|private
specifier|static
name|int
name|helpDeprecatedSize
init|=
name|DEFAULT_HELP_DEPRECATED_SIZE
decl_stmt|;
comment|/**      * The field width for the option specification that is reported when      * the options are listed in the built-in menu.      */
DECL|field|menuOptionSpecificationSize
specifier|private
specifier|static
name|int
name|menuOptionSpecificationSize
init|=
name|DEFAULT_MENU_OPTION_SIZE
decl_stmt|;
comment|/**      * The field width for the type name that is reported when the options      * are listed in the built-in menu.      */
DECL|field|menuTypenameSize
specifier|private
specifier|static
name|int
name|menuTypenameSize
init|=
name|DEFAULT_MENU_TYPENAME_SIZE
decl_stmt|;
comment|/**      * The field width for the description that is reported when the options      * are listed in the built-in menu.      */
DECL|field|menuDescriptionSize
specifier|private
specifier|static
name|int
name|menuDescriptionSize
init|=
name|DEFAULT_MENU_DESCRIPTION_SIZE
decl_stmt|;
comment|/**      * The field width for the deprecated flag that is reported when the      * options are listed in the build-in menu.      */
DECL|field|menuDeprecatedSize
specifier|private
specifier|static
name|int
name|menuDeprecatedSize
init|=
name|DEFAULT_MENU_DEPRECATED_SIZE
decl_stmt|;
comment|/**      * The field width for the assignment portion of an option that is       * written to a file.      */
DECL|field|fileCompleteOptionSize
specifier|private
specifier|static
name|int
name|fileCompleteOptionSize
init|=
name|DEFAULT_FILE_COMPLETE_OPTION_SIZE
decl_stmt|;
comment|/**      * The field width for the comment portion of an option that is written      * to a file.      */
DECL|field|fileCommentSize
specifier|private
specifier|static
name|int
name|fileCommentSize
init|=
name|DEFAULT_FILE_COMMENT_SIZE
decl_stmt|;
comment|/**      * A field indicating whether an option has been invoked.      */
DECL|field|invoked
specifier|protected
name|boolean
name|invoked
decl_stmt|;
comment|/**      * Returns this option's value as an Object.      *      * @return An object representation of this option.      */
DECL|method|getObject ()
specifier|public
specifier|abstract
name|Object
name|getObject
parameter_list|()
function_decl|;
comment|/**      * Returns the option's value as a String. This String should conform      * to the formatting requirements prescribed by a modify method.      *      * @return The option's value as a String conforming to formatting      *         requirements.      */
DECL|method|getStringValue ()
specifier|public
specifier|abstract
name|String
name|getStringValue
parameter_list|()
function_decl|;
comment|/**      * Constructs an option with no initial short or long option value,      * and is by default uninvoked and undeprecated, and has a description      * initialized to the empty string.      */
DECL|method|Option ()
specifier|public
name|Option
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|/**      * Constructs an option by copying the option passed.      *      * @param option  The option to copy for this object's construction.      */
DECL|method|Option ( Option option )
specifier|public
name|Option
parameter_list|(
name|Option
name|option
parameter_list|)
block|{
name|longOption
operator|=
name|option
operator|.
name|getLongOption
argument_list|()
expr_stmt|;
name|shortOption
operator|=
name|option
operator|.
name|getShortOption
argument_list|()
expr_stmt|;
name|description
operator|=
name|option
operator|.
name|getDescription
argument_list|()
expr_stmt|;
name|deprecated
operator|=
name|option
operator|.
name|isDeprecated
argument_list|()
expr_stmt|;
block|}
comment|/**      * Constructs an option by initializing its long option with the      * value passed. The short option is equal to the null character,      * and the description is equal to the empty string.      *      * @param longOption The value of the long option      */
DECL|method|Option ( String longOption )
specifier|public
name|Option
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
name|this
operator|.
name|longOption
operator|=
name|longOption
expr_stmt|;
name|this
operator|.
name|shortOption
operator|=
literal|'\0'
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|/**      * Constructs an option by initializing its short option with the      * value passed. The long option is equal to null, and the description      * is equal to the empty string.      *      * @param shortOption The value of the short option.      */
DECL|method|Option ( char shortOption )
specifier|public
name|Option
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
name|this
operator|.
name|shortOption
operator|=
name|shortOption
expr_stmt|;
name|this
operator|.
name|longOption
operator|=
literal|null
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|/**      * Constructs an option by initializing its short and long options      * with the values passed. The description is set to the empty string.      *      * @param longOption The value of the long option.      * @param shortOption The value of the short option.      */
DECL|method|Option ( String longOption, char shortOption )
specifier|public
name|Option
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
name|this
operator|.
name|longOption
operator|=
name|longOption
expr_stmt|;
name|this
operator|.
name|shortOption
operator|=
name|shortOption
expr_stmt|;
name|description
operator|=
literal|""
expr_stmt|;
block|}
comment|/**      * Sets the long option.      *      * @param longOption The value to set the long option.      */
DECL|method|setKey ( String longOption )
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
name|this
operator|.
name|longOption
operator|=
name|longOption
expr_stmt|;
block|}
comment|/**      * Sets the short option.      *      * @param shortOption The value to set the short option.      */
DECL|method|setKey ( char shortOption )
specifier|public
name|void
name|setKey
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
name|this
operator|.
name|shortOption
operator|=
name|shortOption
expr_stmt|;
block|}
comment|/**      * Sets the short option.      *      * @param shortOption The value to set the short option.      */
DECL|method|setShortOption ( char shortOption )
specifier|public
name|void
name|setShortOption
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
name|setKey
argument_list|(
name|shortOption
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the long option.      *      * @param longOption The value to set the long option.      */
DECL|method|setLongOption ( String longOption )
specifier|public
name|void
name|setLongOption
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
name|setKey
argument_list|(
name|longOption
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the description of this option.      *      * @param description The description of this option.      */
DECL|method|setDescription ( String description )
specifier|public
name|void
name|setDescription
parameter_list|(
name|String
name|description
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
comment|/**      * Sets the deprecated flag to the value passed.      *      * @param deprecated A flag indicating whether the option is deprecated.      */
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
comment|/**      * Sets the field width for the option specification displayed      * in the help report.      *      * @param newSize The size to set the field width.      */
DECL|method|setHelpOptionSpecificationSize ( int newSize )
specifier|public
specifier|static
name|void
name|setHelpOptionSpecificationSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|helpOptionSpecificationSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the type name displayed in the help report.      *      * @param newSize The size to set the field width.      */
DECL|method|setHelpTypenameSize ( int newSize )
specifier|public
specifier|static
name|void
name|setHelpTypenameSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|helpTypenameSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the description displayed in the help report.      *      * @param newSize The size to set the field width.      */
DECL|method|setHelpDescriptionSize ( int newSize )
specifier|public
specifier|static
name|void
name|setHelpDescriptionSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|helpDescriptionSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the deprecated flag displayed in the      * help report.      *      * @param newSize The size to set the field width.      */
DECL|method|setHelpDeprecatedSize ( int newSize )
specifier|public
specifier|static
name|void
name|setHelpDeprecatedSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|helpDeprecatedSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the option specification displayed      * in the menu listing of options.      *      * @param newSize The size to set the field width.      */
DECL|method|setMenuOptionSpecificationSize ( int newSize )
specifier|public
specifier|static
name|void
name|setMenuOptionSpecificationSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|menuOptionSpecificationSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the type name displayed in the menu      * listing of options.      *      * @param newSize The size to set the field width.      */
DECL|method|setMenuTypenameSize ( int newSize )
specifier|public
specifier|static
name|void
name|setMenuTypenameSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|menuTypenameSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the option description displayed      * in the menu listing of options.      *      * @param newSize The size to set the field width.      */
DECL|method|setMenuDescriptionSize ( int newSize )
specifier|public
specifier|static
name|void
name|setMenuDescriptionSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|menuDescriptionSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the field width for the deprecated flag displayed      * in the menu listing of options.      *      * @param newSize The size to set the field width.      */
DECL|method|setMenuDeprecatedSize ( int newSize )
specifier|public
specifier|static
name|void
name|setMenuDeprecatedSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|menuDeprecatedSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the assignment field width used when options files are written.      *      * @param newSize The size to set the field width.      */
DECL|method|setFileCompleteOptionSize ( int newSize )
specifier|public
specifier|static
name|void
name|setFileCompleteOptionSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|fileCompleteOptionSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets the assignment field width used when options files are written.      *      * @param newSize The size to set the field width.      */
DECL|method|setFileCommentSize ( int newSize )
specifier|public
specifier|static
name|void
name|setFileCommentSize
parameter_list|(
name|int
name|newSize
parameter_list|)
block|{
name|fileCommentSize
operator|=
name|newSize
expr_stmt|;
block|}
comment|/**      * Sets whether this option has been invoked.      *      * @param A boolean indicating whether this option has been invoked.      */
DECL|method|setInvoked ( boolean b )
specifier|public
name|void
name|setInvoked
parameter_list|(
name|boolean
name|b
parameter_list|)
block|{
name|invoked
operator|=
name|b
expr_stmt|;
block|}
comment|/**      * Deprecates this option.      */
DECL|method|deprecate ()
specifier|public
name|void
name|deprecate
parameter_list|()
block|{
name|setDeprecated
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Return the name of this option. This method returns the same value as      * the getLongOption accessor.      *      * @return The name of this otpion.      */
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|longOption
return|;
block|}
comment|/**      * Return the short option key. There is no short option when this      * character is the null character.      *      * @return The short option key of this option.      */
DECL|method|getShortOption ()
specifier|public
name|char
name|getShortOption
parameter_list|()
block|{
return|return
name|shortOption
return|;
block|}
comment|/**      * Return the long option key. There is no long option when this value      * is null.      *      * @return The long option key of this option.      */
DECL|method|getLongOption ()
specifier|public
name|String
name|getLongOption
parameter_list|()
block|{
return|return
name|longOption
return|;
block|}
comment|/**      * Return a line used for help reporting.      *      * @return A line used for help reporting.      */
DECL|method|getHelp ()
specifier|public
name|String
name|getHelp
parameter_list|()
block|{
return|return
name|getHelpOptionSpecification
argument_list|()
operator|+
literal|" "
operator|+
name|getHelpTypeName
argument_list|()
operator|+
literal|" "
operator|+
name|getHelpDescription
argument_list|()
operator|+
literal|" "
operator|+
name|getHelpDeprecated
argument_list|()
return|;
block|}
comment|/**      * Return the option specification field used during help reporting.      *      * @return The option specification field.      */
DECL|method|getHelpOptionSpecification ()
specifier|public
name|String
name|getHelpOptionSpecification
parameter_list|()
block|{
return|return
name|Utility
operator|.
name|expandString
argument_list|(
operator|(
operator|(
operator|(
name|shortOption
operator|!=
literal|'\0'
operator|)
condition|?
operator|(
literal|"-"
operator|+
name|getShortOption
argument_list|()
operator|)
else|:
literal|"  "
operator|)
operator|+
operator|(
operator|(
name|longOption
operator|!=
literal|null
operator|&&
name|shortOption
operator|!=
literal|'\0'
operator|)
condition|?
literal|", "
else|:
literal|"  "
operator|)
operator|+
operator|(
operator|(
name|longOption
operator|!=
literal|null
operator|)
condition|?
literal|"--"
operator|+
name|getLongOption
argument_list|()
else|:
literal|""
operator|)
operator|)
argument_list|,
name|helpOptionSpecificationSize
argument_list|)
return|;
block|}
comment|/**      * Return the type name field used during help reporting.      *      * @return The type name field.      */
DECL|method|getHelpTypeName ()
specifier|public
name|String
name|getHelpTypeName
parameter_list|()
block|{
return|return
name|Utility
operator|.
name|expandString
argument_list|(
literal|"<"
operator|+
name|getTypeName
argument_list|()
operator|+
literal|">"
argument_list|,
name|helpTypenameSize
argument_list|)
return|;
block|}
comment|/**      * Return the description field used during help reporting.      *      * @return The description field.      */
DECL|method|getHelpDescription ()
specifier|public
name|String
name|getHelpDescription
parameter_list|()
block|{
return|return
name|Utility
operator|.
name|expandString
argument_list|(
name|getDescription
argument_list|()
argument_list|,
name|helpDescriptionSize
argument_list|)
return|;
block|}
comment|/**      * Return the deprecated field used during help reporting.      *      * @return The deprecated field.      */
DECL|method|getHelpDeprecated ()
specifier|public
name|String
name|getHelpDeprecated
parameter_list|()
block|{
return|return
name|Utility
operator|.
name|expandString
argument_list|(
name|isDeprecated
argument_list|()
condition|?
literal|"[d]"
else|:
literal|""
argument_list|,
name|helpDeprecatedSize
argument_list|)
return|;
block|}
comment|/**      * Return the header displayed at the top of the help report.      *      * @return The header displayed at the top of the help report.      */
DECL|method|getHelpHeader ()
specifier|public
specifier|static
name|String
name|getHelpHeader
parameter_list|()
block|{
return|return
name|Utility
operator|.
name|expandString
argument_list|(
literal|"Option Name"
argument_list|,
name|helpOptionSpecificationSize
argument_list|)
operator|+
literal|" "
operator|+
name|Utility
operator|.
name|expandString
argument_list|(
literal|"Type"
argument_list|,
name|helpTypenameSize
argument_list|)
operator|+
literal|" "
operator|+
name|Utility
operator|.
name|expandString
argument_list|(
literal|"Description"
argument_list|,
name|helpDescriptionSize
argument_list|)
return|;
block|}
comment|/**      * The description explaining the meaning of this option.      *      * @return This options description.      */
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|description
return|;
block|}
comment|/**      * The hash key of this option. This is used by classes that implement      * the option registrar class. This method should<b>not</b> be overrided.      *      * @return The hash key of this option.      */
DECL|method|getHashKey ()
specifier|public
name|String
name|getHashKey
parameter_list|()
block|{
return|return
name|Option
operator|.
name|getHashKey
argument_list|(
name|longOption
argument_list|,
name|shortOption
argument_list|)
return|;
block|}
comment|/**      * The hash key of an option if there is no short option. This method      * should<b>not</b> be overrided.      *      * @param longOption The long option.      *      * @return The hash key of this option based on the long option.      */
DECL|method|getHashKey ( String longOption )
specifier|public
specifier|static
name|String
name|getHashKey
parameter_list|(
name|String
name|longOption
parameter_list|)
block|{
return|return
literal|","
operator|+
operator|(
operator|(
name|longOption
operator|!=
literal|null
operator|)
condition|?
name|longOption
else|:
literal|""
operator|)
return|;
block|}
comment|/**      * The hash key of an option if there is no long option. This method      * should<b>not</b> be overrided.      *      * @param shortOption The short option.      *      * @return The hash key of this option based on the short option.      */
DECL|method|getHashKey ( char shortOption )
specifier|public
specifier|static
name|String
name|getHashKey
parameter_list|(
name|char
name|shortOption
parameter_list|)
block|{
return|return
literal|""
operator|+
operator|(
name|shortOption
operator|!=
literal|'\0'
operator|)
operator|+
literal|","
return|;
block|}
comment|/**      * The hash key of an option if there both short and long options are      * defined.      *      * @param shortOption The short option.      * @param longOption  The long option.      *      * @return The hash key of this option based on both the short and long      *         options.      */
DECL|method|getHashKey ( String longOption, char shortOption )
specifier|public
specifier|static
name|String
name|getHashKey
parameter_list|(
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
return|return
operator|(
operator|(
name|shortOption
operator|==
literal|'\0'
operator|)
condition|?
literal|""
else|:
literal|""
operator|+
name|shortOption
operator|)
operator|+
operator|(
operator|(
name|longOption
operator|==
literal|null
operator|)
condition|?
literal|","
else|:
literal|","
operator|+
name|longOption
operator|)
return|;
block|}
comment|/**      * Returns whether this option is deprecated.      *      * @return A boolean indicating whether this option is deprecated.      */
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
comment|/**      * Returns whether this option has been invoked.      *      * @return A boolean indicating whether this option has been invoked.      */
DECL|method|isInvoked ()
specifier|public
name|boolean
name|isInvoked
parameter_list|()
block|{
return|return
name|invoked
return|;
block|}
comment|/**      * Returns (a) line(s) representing this option. This line is usually      * later written to an options file.      *      * @return Line(s) representing this option.      */
DECL|method|getOptionFileLine ()
specifier|public
name|String
name|getOptionFileLine
parameter_list|()
block|{
name|boolean
name|descriptionPrinted
init|=
literal|false
decl_stmt|;
name|String
name|retval
init|=
literal|""
decl_stmt|;
name|String
name|optionText
init|=
literal|""
decl_stmt|;
name|String
name|strval
init|=
name|getStringValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|longOption
operator|!=
literal|null
condition|)
block|{
name|optionText
operator|+=
literal|"--"
operator|+
name|longOption
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|shortOption
operator|!=
literal|'\0'
condition|)
block|{
name|optionText
operator|+=
literal|"-"
operator|+
name|shortOption
expr_stmt|;
block|}
if|if
condition|(
name|optionText
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|&&
name|Utility
operator|.
name|trim
argument_list|(
name|strval
argument_list|)
operator|.
name|length
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|optionText
operator|+=
literal|"="
operator|+
name|strval
expr_stmt|;
block|}
if|if
condition|(
name|optionText
operator|.
name|length
argument_list|()
operator|<=
name|fileCompleteOptionSize
condition|)
block|{
name|retval
operator|+=
name|Utility
operator|.
name|expandString
argument_list|(
name|optionText
argument_list|,
name|fileCompleteOptionSize
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|retval
operator|+=
literal|"; "
operator|+
name|description
operator|+
literal|"\n"
expr_stmt|;
name|retval
operator|+=
name|optionText
expr_stmt|;
name|descriptionPrinted
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|descriptionPrinted
condition|)
block|{
name|StringBuffer
name|descsplit
init|=
operator|new
name|StringBuffer
argument_list|(
name|description
argument_list|)
decl_stmt|;
name|boolean
name|tmp
init|=
literal|false
decl_stmt|;
while|while
condition|(
name|descsplit
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
name|st
init|=
literal|""
decl_stmt|;
name|int
name|size
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|tmp
condition|)
block|{
name|st
operator|+=
name|Utility
operator|.
name|getSpaces
argument_list|(
name|fileCompleteOptionSize
argument_list|)
expr_stmt|;
block|}
name|size
operator|=
operator|(
name|descsplit
operator|.
name|length
argument_list|()
operator|>=
name|fileCommentSize
operator|)
condition|?
name|fileCommentSize
else|:
name|descsplit
operator|.
name|length
argument_list|()
expr_stmt|;
name|st
operator|+=
literal|"; "
operator|+
name|descsplit
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|size
argument_list|)
expr_stmt|;
name|descsplit
operator|.
name|delete
argument_list|(
literal|0
argument_list|,
name|size
argument_list|)
expr_stmt|;
name|retval
operator|+=
name|st
operator|+
literal|"\n"
expr_stmt|;
name|tmp
operator|=
literal|true
expr_stmt|;
block|}
name|descriptionPrinted
operator|=
literal|true
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
comment|/**      * Returns the field width for the option specification displayed in the      * help report.      *      * @return The field width.      */
DECL|method|getHelpOptionSpecificationSize ()
specifier|public
specifier|static
name|int
name|getHelpOptionSpecificationSize
parameter_list|()
block|{
return|return
name|helpOptionSpecificationSize
return|;
block|}
comment|/**      * Returns the field width for the type name displayed in the help report.      *      * @return The field width.      */
DECL|method|getHelpTypenameSize ()
specifier|public
specifier|static
name|int
name|getHelpTypenameSize
parameter_list|()
block|{
return|return
name|helpTypenameSize
return|;
block|}
comment|/**      * Returns the field width for the description displayed in the help      * report.      *      * @return The field width.      */
DECL|method|getHelpDescriptionSize ()
specifier|public
specifier|static
name|int
name|getHelpDescriptionSize
parameter_list|()
block|{
return|return
name|helpDescriptionSize
return|;
block|}
comment|/**      * Returns the field width for the deprecated flag displayed in the      * help report.      *      * @return The field width.      */
DECL|method|getHelpDeprecatedSize ()
specifier|public
specifier|static
name|int
name|getHelpDeprecatedSize
parameter_list|()
block|{
return|return
name|helpDeprecatedSize
return|;
block|}
comment|/**      * Returns the field width for the option specification displayed in the      * menu listing of options.      *      * @return The field width.      */
DECL|method|getMenuOptionSpecificationSize ()
specifier|public
specifier|static
name|int
name|getMenuOptionSpecificationSize
parameter_list|()
block|{
return|return
name|menuOptionSpecificationSize
return|;
block|}
comment|/**      * Returns the field width for the type name displayed in the      * menu listing of options.      *      * @return The field width.      */
DECL|method|getMenuTypenameSize ()
specifier|public
specifier|static
name|int
name|getMenuTypenameSize
parameter_list|()
block|{
return|return
name|menuTypenameSize
return|;
block|}
comment|/**      * Returns the field width for the description displayed in the      * menu listing of options.      *      * @return The field width.      */
DECL|method|getMenuDescriptionSize ()
specifier|public
specifier|static
name|int
name|getMenuDescriptionSize
parameter_list|()
block|{
return|return
name|menuDescriptionSize
return|;
block|}
comment|/**      * Returns the field width for the deprecated flag displayed in the      * menu listing of options.      *      * @return The field width.      */
DECL|method|getMenuDeprecatedSize ()
specifier|public
specifier|static
name|int
name|getMenuDeprecatedSize
parameter_list|()
block|{
return|return
name|menuDeprecatedSize
return|;
block|}
comment|/**      * Returns the field width for assignment portion of a option file line.      *      * @return The field width.      */
DECL|method|getFileCompleteOptionSize ()
specifier|public
specifier|static
name|int
name|getFileCompleteOptionSize
parameter_list|()
block|{
return|return
name|fileCompleteOptionSize
return|;
block|}
comment|/**      * Returns the field width for assignment portion of a option file line.      *      * @return The field width.      */
DECL|method|getFileCommentSize ()
specifier|public
specifier|static
name|int
name|getFileCommentSize
parameter_list|()
block|{
return|return
name|fileCommentSize
return|;
block|}
comment|/**      * Returns the type name of this option.      *      * @return The type name of this option.      */
DECL|method|getTypeName ()
specifier|public
specifier|abstract
name|String
name|getTypeName
parameter_list|()
function_decl|;
comment|/**      * Prepares the option for modification.      */
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
if|if
condition|(
name|deprecated
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|print
argument_list|(
literal|"Warning: "
argument_list|)
expr_stmt|;
if|if
condition|(
name|longOption
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|print
argument_list|(
literal|"--"
operator|+
name|longOption
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|shortOption
operator|!=
literal|'\0'
operator|&&
name|longOption
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|print
argument_list|(
literal|" or "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|shortOption
operator|!=
literal|'\0'
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"-"
operator|+
name|shortOption
operator|+
literal|" is deprecated."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

begin_comment
comment|/** Option */
end_comment

end_unit

