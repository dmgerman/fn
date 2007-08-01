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
comment|/**  * SimpleProcess.java  *  * Version:  *   $Id$  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|OutputStream
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

begin_comment
comment|/**  * A SimpleProcess is used to execute a shell process, and redirect an  * input stream to the processes' standard input, as well as redirect  * the processes' standard output/error to an output stream. The processes  * is multithreaded to prevent deadlock.<p>  *  * The example below demonstrates the use of this class.  *<pre>  *  class ExecuteProcess {  *       public static void main( String args[] ) {  *           if ( args.length> 0 ) {  *               String processName = args[ 0 ];  *               try {  *                   SimpleProcess process  *                      = new SimpleProcess( Runtime.getRuntime.exec(  *                                                            processName ) );  *                                          );  *                   int exitStatus = process.waitFor();  *                   System.out.println( "The process ran successfully"   *                                       + " with an exit status of "  *                                       + exitStatus + "." );  *               }  *               catch ( Exception e ) {  *                   System.out.println( "The process was not successful. "  *                                       + " Reason: " + e.getMessage() );  *               }  *           }  *           else {  *               System.err.println( "Please specify a command" );  *           }  *       }  *  }  *</pre>  *  *<hr>  *  *<pre>  * Copyright (C) Damian Ryan Eads, 2001. All Rights Reserved.  *  * ritopt is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.   * ritopt is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License  * along with ritopt; if not, write to the Free Software  * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA  *</pre>  *  * @author Damian Eads  */
end_comment

begin_class
DECL|class|SimpleProcess
specifier|public
class|class
name|SimpleProcess
extends|extends
name|Process
block|{
comment|/**      * The target process.      */
DECL|field|process
specifier|private
name|Process
name|process
decl_stmt|;
comment|/**      * The input stream that the processes' standard input will use.      */
DECL|field|processInput
specifier|private
name|InputStream
name|processInput
decl_stmt|;
comment|/**      * The print stream to redirect to.      */
DECL|field|yourOutput
specifier|private
name|PrintStream
name|yourOutput
decl_stmt|;
comment|/**      * The print stream to redirect to.      */
DECL|field|yourError
specifier|private
name|PrintStream
name|yourError
decl_stmt|;
comment|/**      * The StreamPrinters.      */
DECL|field|in
DECL|field|out
DECL|field|error
specifier|private
name|StreamPrinter
name|in
decl_stmt|,
name|out
decl_stmt|,
name|error
decl_stmt|;
comment|/**      * Constructs a SimpleProcess, redirecting System.in to the its standard      * input, System.out to its standard output, and System.err to its standard      * error.      */
DECL|method|SimpleProcess ( Process process )
specifier|public
name|SimpleProcess
parameter_list|(
name|Process
name|process
parameter_list|)
throws|throws
name|IOException
block|{
name|this
argument_list|(
name|process
argument_list|,
name|System
operator|.
name|in
argument_list|,
name|System
operator|.
name|out
argument_list|,
name|System
operator|.
name|err
argument_list|)
expr_stmt|;
block|}
comment|/**      * Constructs a SimpleProcess, initializing it with the streams passed.      *      * @param process       The target process.      * @param processInput  The stream that is redirected to the      *                      processes' standard input.      * @param processOutput The stream to redirect the processes's      *                      standard output.      * @param processError  The stream to redirect the processes's      *                      standard input.      */
DECL|method|SimpleProcess ( Process process, InputStream processInput, PrintStream yourOutput, PrintStream yourError )
specifier|public
name|SimpleProcess
parameter_list|(
name|Process
name|process
parameter_list|,
name|InputStream
name|processInput
parameter_list|,
name|PrintStream
name|yourOutput
parameter_list|,
name|PrintStream
name|yourError
parameter_list|)
throws|throws
name|IOException
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|process
operator|=
name|process
expr_stmt|;
name|this
operator|.
name|processInput
operator|=
name|processInput
expr_stmt|;
name|this
operator|.
name|yourOutput
operator|=
name|yourOutput
expr_stmt|;
name|this
operator|.
name|yourError
operator|=
name|yourError
expr_stmt|;
block|}
comment|/**      * Returns the standard input of this process.      *      * @return The standard input of this process.      */
DECL|method|getOutputStream ()
specifier|public
name|OutputStream
name|getOutputStream
parameter_list|()
block|{
return|return
name|process
operator|.
name|getOutputStream
argument_list|()
return|;
block|}
comment|/**      * Returns the standard output of this process.      *      * @return The standard output of this process.      */
DECL|method|getInputStream ()
specifier|public
name|InputStream
name|getInputStream
parameter_list|()
block|{
return|return
name|process
operator|.
name|getInputStream
argument_list|()
return|;
block|}
comment|/**      * Returns the standard error of this process.      *      * @return The standard error of this process.      */
DECL|method|getErrorStream ()
specifier|public
name|InputStream
name|getErrorStream
parameter_list|()
block|{
return|return
name|process
operator|.
name|getErrorStream
argument_list|()
return|;
block|}
comment|/**      * Begin redirecting the streams passed. This method should be invoked      * immediately after execution of a simple process to prevent thread      * deadlock.      *      * @return The exit status of the target process.      */
DECL|method|waitFor ()
specifier|public
name|int
name|waitFor
parameter_list|()
throws|throws
name|InterruptedException
block|{
name|int
name|retval
init|=
name|waitForImpl
argument_list|()
decl_stmt|;
if|if
condition|(
name|in
operator|!=
literal|null
condition|)
block|{
name|in
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
return|return
name|retval
return|;
block|}
comment|/**      * Contains the implementation of wait for.      *      * @return The exit status of the target process.      */
DECL|method|waitForImpl ()
specifier|private
name|int
name|waitForImpl
parameter_list|()
throws|throws
name|InterruptedException
block|{
name|process
operator|=
name|process
expr_stmt|;
name|in
operator|=
operator|new
name|StreamPrinter
argument_list|(
name|processInput
argument_list|,
operator|new
name|PrintStream
argument_list|(
name|process
operator|.
name|getOutputStream
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|in
operator|.
name|setFlush
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|out
operator|=
operator|new
name|StreamPrinter
argument_list|(
name|process
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|yourOutput
argument_list|)
expr_stmt|;
name|error
operator|=
operator|new
name|StreamPrinter
argument_list|(
name|process
operator|.
name|getErrorStream
argument_list|()
argument_list|,
name|yourError
argument_list|)
expr_stmt|;
name|in
operator|.
name|start
argument_list|()
expr_stmt|;
name|out
operator|.
name|start
argument_list|()
expr_stmt|;
name|error
operator|.
name|start
argument_list|()
expr_stmt|;
name|out
operator|.
name|join
argument_list|()
expr_stmt|;
name|error
operator|.
name|join
argument_list|()
expr_stmt|;
return|return
name|process
operator|.
name|waitFor
argument_list|()
return|;
block|}
comment|/**      * Returns the target processes' exit value.      *      * @return This processes' exit value.      */
DECL|method|exitValue ()
specifier|public
name|int
name|exitValue
parameter_list|()
block|{
return|return
name|process
operator|.
name|exitValue
argument_list|()
return|;
block|}
comment|/**      * Destroys the target process.      */
DECL|method|destroy ()
specifier|public
name|void
name|destroy
parameter_list|()
throws|throws
name|IllegalThreadStateException
block|{
name|process
operator|.
name|destroy
argument_list|()
expr_stmt|;
block|}
block|}
end_class

begin_comment
comment|/** SimpleProcess **/
end_comment

end_unit

