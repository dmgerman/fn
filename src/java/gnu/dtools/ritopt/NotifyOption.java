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
comment|/**  * The NotifyOption class is used to register options that when invoked  * notify a listener. This provides an interface for event-driven  * options processing. In order for a class to be notified, it must implement  * the OptionListener interface.<p>  *  * When the option is invoked, the corresponding short, long, and option  * values are put in an OptionEvent object, and passed to all registered  * listeners.<p>  *  * A class must implement the OptionListener interface in order to receive  * notification of option events.<p>  *  * For a more detailed explanation please refer to the tutorial. The following  * is a simple example of how a NotifyOption is used.  *<pre>  *  * import gnu.dtools.ritopt.*;  *  * public class TellMe implements OptionListener {  *  *    public static void main( String args[] ) {  *       TellMe m = new TellMe();  *       Options processor = new Options();  *       NotifyOption say = new NotifyOption( m );  *       processor.register( "say", 's', say );  *       processor.process();  *    }  *  *    public void optionInvoked( OptionEvent e ) {  *       if ( e.getCommand().equals( "say" ) ) {  *           String say = e.getValue();  *           if ( Utility.trim( say ).length() == 0 ) say = "nothing";  *           System.err.println( "You told me to say " + nothing + "." );  *       }  *    }  * }  *  * cookies@crazymonster$ javac TellMe.java  * cookies@crazymonster$ java TellMe  * cookies@crazymonster$ java TellMe  * cookies@crazymonster$ java TellMe --say -s  * You told me to say nothing.  * You told me to say nothing.  * cookies@crazymonster$ java TellMe --say hello  * You told me to say hello.  * cookies@crazymonster$ java TellMe --say "I'm sorry"  * You told me to say I'm sorry.  * cookies@crazymonster$ java TellMe --say="not until tomorrow" -s "I'm crazy"  * You told me to say not until tomorrow.  * You told me to say I'm crazy.  *</pre>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|NotifyOption
specifier|public
class|class
name|NotifyOption
extends|extends
name|Option
implements|implements
name|OptionNotifier
block|{
comment|/**      * The current value of the notify option.      */
DECL|field|value
specifier|private
name|String
name|value
init|=
literal|""
decl_stmt|;
comment|/**      * The default command if a command is not specified.      */
DECL|field|command
specifier|private
name|String
name|command
init|=
literal|"Default"
decl_stmt|;
comment|/**      * A list of listeners to notify whenever a modification event occurs.      */
DECL|field|listeners
specifier|private
name|java
operator|.
name|util
operator|.
name|List
name|listeners
decl_stmt|;
comment|/**      * Construct a NotifyOption with an empty list of listeners. Set the      * initial value to null.      */
DECL|method|NotifyOption ()
specifier|public
name|NotifyOption
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct a NotifyOption and register the passed listener.      *      * @param listener    The listener to register.      */
DECL|method|NotifyOption ( OptionListener listener )
specifier|public
name|NotifyOption
parameter_list|(
name|OptionListener
name|listener
parameter_list|)
block|{
name|this
argument_list|(
name|listener
argument_list|,
literal|"Default"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct a NotifyOption and register the passed listener. Initialize      * the command to the value passed.      *      * @param listener    The listener to register.      * @param command     The value of the command.      */
DECL|method|NotifyOption ( OptionListener listener, String command )
specifier|public
name|NotifyOption
parameter_list|(
name|OptionListener
name|listener
parameter_list|,
name|String
name|command
parameter_list|)
block|{
name|this
argument_list|(
name|listener
argument_list|,
name|command
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct a NotifyOption and register the passed listener. Initialize      * the command to the value passed.      *      * @param listener    The listener to register.      * @param command     The value of the command.      * @param value       The default value of the option.      */
DECL|method|NotifyOption ( OptionListener listener, String command, String value )
specifier|public
name|NotifyOption
parameter_list|(
name|OptionListener
name|listener
parameter_list|,
name|String
name|command
parameter_list|,
name|String
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
name|listeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct a NotifyOption by copying the NotifyOption passed.      *      * @param op   The notify option to copy.      */
DECL|method|NotifyOption ( NotifyOption op )
specifier|public
name|NotifyOption
parameter_list|(
name|NotifyOption
name|op
parameter_list|)
block|{
name|super
argument_list|(
name|op
argument_list|)
expr_stmt|;
name|op
operator|.
name|value
operator|=
operator|new
name|String
argument_list|(
name|op
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|listeners
operator|=
operator|new
name|java
operator|.
name|util
operator|.
name|ArrayList
argument_list|(
name|op
operator|.
name|listeners
argument_list|)
expr_stmt|;
block|}
comment|/**      * Construct a NotifyOption, and initialize its default value to the      * value passed.      *      * @param value   The default value of this option.      */
DECL|method|NotifyOption ( String value )
specifier|public
name|NotifyOption
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a NotifyOption option initialized with the value and      * long option passed.      *      * @param value      The initial value of this notify option.      * @param longOption The long option associated with this notify option.      */
DECL|method|NotifyOption ( String value, String longOption )
specifier|public
name|NotifyOption
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|longOption
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
name|longOption
argument_list|,
literal|'\0'
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a character option initialized with the value and      * short option passed.      *      * @param value       The initial value of this NotifyOption option.      * @param shortOption The short option associated with this option.      */
DECL|method|NotifyOption ( String value, char shortOption )
specifier|public
name|NotifyOption
parameter_list|(
name|String
name|value
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
name|this
argument_list|(
name|value
argument_list|,
literal|null
argument_list|,
name|shortOption
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs an NotifyOption option initialized with the value, short      * and long option passed.      *      * @param shortOption The short option associated with this option.      * @param longOption  The long option associated with this option.      * @param value       The initial value of this NotifyOption option.      */
DECL|method|NotifyOption ( String value, String longOption, char shortOption )
specifier|public
name|NotifyOption
parameter_list|(
name|String
name|value
parameter_list|,
name|String
name|longOption
parameter_list|,
name|char
name|shortOption
parameter_list|)
block|{
name|super
argument_list|(
name|longOption
argument_list|,
name|shortOption
argument_list|)
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|listeners
operator|=
operator|new
name|java
operator|.
name|util
operator|.
name|ArrayList
argument_list|()
expr_stmt|;
block|}
comment|/**      * Return the value as an object.      *      * @return This value as an option.      */
DECL|method|getObject ()
specifier|public
name|Object
name|getObject
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * Modify this option based on a string representation.      *      * @param     value String representation of the object.      * @exception OptionModificationException Thrown if an error occurs      *                                  during modification of an option.      */
DECL|method|modify ( String value )
specifier|public
name|void
name|modify
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|OptionModificationException
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
name|java
operator|.
name|util
operator|.
name|Iterator
name|iterator
init|=
name|listeners
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|OptionEvent
name|event
init|=
operator|new
name|OptionEvent
argument_list|(
name|command
argument_list|,
name|value
argument_list|,
name|this
argument_list|)
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|OptionListener
name|listener
init|=
operator|(
name|OptionListener
operator|)
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|listener
operator|.
name|optionInvoked
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Modify this option based on a string representation.      *      * @param     value String representation of the object.      * @exception OptionModificationException Thrown if an error occurs      *                                  during modification of an option.      */
DECL|method|setValue ( String value )
specifier|public
name|void
name|setValue
parameter_list|(
name|String
name|value
parameter_list|)
throws|throws
name|OptionModificationException
block|{
name|modify
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
comment|/**      * Return this option as a string.      *      * @return This option as a string.      */
DECL|method|getValue ()
specifier|public
name|String
name|getValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * Return this option as a string.      *      * @return This option as a string.      */
DECL|method|getStringValue ()
specifier|public
name|String
name|getStringValue
parameter_list|()
block|{
return|return
name|value
return|;
block|}
comment|/**      * Returns the type name of this option. For an NotifyOption, "NOTIFY"      * is returned.      *      * @return The type name of this option.      */
DECL|method|getTypeName ()
specifier|public
name|String
name|getTypeName
parameter_list|()
block|{
return|return
literal|"NOTIFY"
return|;
block|}
comment|/**      * Adds an OptionListener to the notification list.      *      * @param listener The OptionListener to add.      */
DECL|method|addOptionListener ( OptionListener listener )
specifier|public
name|void
name|addOptionListener
parameter_list|(
name|OptionListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|add
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Removes an OptionListener from the notification list.      *      * @param listener The OptionListener to remove.      */
DECL|method|removeOptionListener ( OptionListener listener )
specifier|public
name|void
name|removeOptionListener
parameter_list|(
name|OptionListener
name|listener
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the command sent when an option is invoked.      *      * @param command  The command to send.      */
DECL|method|setOptionCommand ( String command )
specifier|public
name|void
name|setOptionCommand
parameter_list|(
name|String
name|command
parameter_list|)
block|{
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
block|}
comment|/**      * Returns a string representation of this object.      *      * @return A string representation of this object.      */
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|value
return|;
block|}
block|}
end_class

begin_comment
comment|/** NotifyOption */
end_comment

end_unit

