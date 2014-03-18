begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedWriter
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
name|FileWriter
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_class
DECL|class|PushToLyx
specifier|public
class|class
name|PushToLyx
implements|implements
name|PushToApplication
block|{
DECL|field|lyxPipe
specifier|private
name|JTextField
name|lyxPipe
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|field|settings
specifier|private
name|JPanel
name|settings
init|=
literal|null
decl_stmt|;
DECL|field|couldNotFindPipe
specifier|private
name|boolean
name|couldNotFindPipe
init|=
literal|false
decl_stmt|;
DECL|field|couldNotWrite
specifier|private
name|boolean
name|couldNotWrite
init|=
literal|false
decl_stmt|;
DECL|field|message
specifier|private
name|String
name|message
init|=
literal|""
decl_stmt|;
DECL|method|pushEntries (BibtexDatabase database, final BibtexEntry[] entries, final String keyString, MetaData metaData)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
specifier|final
name|BibtexEntry
index|[]
name|entries
parameter_list|,
specifier|final
name|String
name|keyString
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|couldNotFindPipe
operator|=
literal|false
expr_stmt|;
name|couldNotWrite
operator|=
literal|false
expr_stmt|;
name|String
name|lyxpipeSetting
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|lyxpipeSetting
operator|.
name|endsWith
argument_list|(
literal|".in"
argument_list|)
condition|)
name|lyxpipeSetting
operator|=
name|lyxpipeSetting
operator|+
literal|".in"
expr_stmt|;
name|File
name|lp
init|=
operator|new
name|File
argument_list|(
name|lyxpipeSetting
argument_list|)
decl_stmt|;
comment|// this needs to fixed because it gives "asdf" when going prefs.get("lyxpipe")
if|if
condition|(
operator|!
name|lp
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|lp
operator|.
name|canWrite
argument_list|()
condition|)
block|{
comment|// See if it helps to append ".in":
name|lp
operator|=
operator|new
name|File
argument_list|(
name|lyxpipeSetting
operator|+
literal|".in"
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|lp
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|lp
operator|.
name|canWrite
argument_list|()
condition|)
block|{
name|couldNotFindPipe
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
specifier|final
name|File
name|lyxpipe
init|=
name|lp
decl_stmt|;
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|lyxpipe
argument_list|)
decl_stmt|;
name|BufferedWriter
name|lyx_out
init|=
operator|new
name|BufferedWriter
argument_list|(
name|fw
argument_list|)
decl_stmt|;
name|String
name|citeStr
init|=
literal|""
decl_stmt|;
name|citeStr
operator|=
literal|"LYXCMD:sampleclient:citation-insert:"
operator|+
name|keyString
expr_stmt|;
name|lyx_out
operator|.
name|write
argument_list|(
name|citeStr
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|lyx_out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotWrite
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
argument_list|)
decl_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
comment|//new Timeout(2000, t, Globals.lang("Error")+": "+
comment|//Globals.lang("unable to access LyX-pipe"));
try|try
block|{
name|t
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert selected citations into LyX/Kile"
argument_list|)
return|;
block|}
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"LyX/Kile"
return|;
block|}
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Push selection to LyX/Kile"
argument_list|)
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"lyx"
argument_list|)
return|;
block|}
DECL|method|getKeyStrokeName ()
specifier|public
name|String
name|getKeyStrokeName
parameter_list|()
block|{
return|return
literal|"Push to LyX"
return|;
block|}
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|couldNotFindPipe
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"verify that LyX is running and that the lyxpipe is valid"
argument_list|)
operator|+
literal|". ["
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
operator|+
literal|"]"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotWrite
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"unable to write to"
argument_list|)
operator|+
literal|" "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
operator|+
literal|".in"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Pushed the citations for the following rows to"
argument_list|)
operator|+
literal|" Lyx: "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
if|if
condition|(
name|settings
operator|==
literal|null
condition|)
name|initSettingsPanel
argument_list|()
expr_stmt|;
name|lyxPipe
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"lyxpipe"
argument_list|,
name|lyxPipe
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|initSettingsPanel ()
specifier|private
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|settings
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to LyX pipe"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|lyxPipe
argument_list|)
expr_stmt|;
block|}
comment|/*class Timeout extends javax.swing.Timer     {       public Timeout(int timeout, final Thread toStop, final String message) {         super(timeout, new ActionListener() {           public void actionPerformed(ActionEvent e) {             toStop.stop();         // !!!<- deprecated             // toStop.interrupt(); // better ?, interrupts wait and IO             //stop();             //output(message);           }         });       }     } */
block|}
end_class

end_unit

