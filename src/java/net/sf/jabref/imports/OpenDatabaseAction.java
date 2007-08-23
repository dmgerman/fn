begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|FileLinksUpgradeWarning
import|;
end_import

begin_comment
comment|// The action concerned with opening an existing database.
end_comment

begin_class
DECL|class|OpenDatabaseAction
specifier|public
class|class
name|OpenDatabaseAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|showDialog
name|boolean
name|showDialog
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
comment|// List of actions that may need to be called after opening the file. Such as
comment|// upgrade actions etc. that may depend on the JabRef version that wrote the file:
DECL|field|postOpenActions
specifier|private
specifier|static
name|ArrayList
argument_list|<
name|PostOpenAction
argument_list|>
name|postOpenActions
init|=
operator|new
name|ArrayList
argument_list|<
name|PostOpenAction
argument_list|>
argument_list|()
decl_stmt|;
static|static
block|{
comment|// Add the action for checking for new custom entry types loaded from
comment|// the bib file:
name|postOpenActions
operator|.
name|add
argument_list|(
operator|new
name|CheckForNewEntryTypesAction
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the action for the new external file handling system in version 2.3:
name|postOpenActions
operator|.
name|add
argument_list|(
operator|new
name|FileLinksUpgradeWarning
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|OpenDatabaseAction (JabRefFrame frame, boolean showDialog)
specifier|public
name|OpenDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|showDialog
parameter_list|)
block|{
name|super
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"open"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|showDialog
operator|=
name|showDialog
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
literal|"Open database"
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Open database"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open BibTeX database"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
comment|//File fileToOpen = null;
if|if
condition|(
name|showDialog
condition|)
block|{
name|String
index|[]
name|chosen
init|=
name|Globals
operator|.
name|getMultipleFiles
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"workingDirectory"
argument_list|)
argument_list|)
argument_list|,
literal|".bib"
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|chosen
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|chosen
index|[
name|i
index|]
operator|!=
literal|null
condition|)
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|chosen
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/*             String chosenFile = Globals.getNewFile(frame,                     new File(Globals.prefs.get("workingDirectory")), ".bib",                     JFileChooser.OPEN_DIALOG, true);              if (chosenFile != null) {                 fileToOpen = new File(chosenFile);             }*/
block|}
else|else
block|{
name|Util
operator|.
name|pr
argument_list|(
name|NAME
argument_list|)
expr_stmt|;
name|Util
operator|.
name|pr
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
expr_stmt|;
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|Util
operator|.
name|checkName
argument_list|(
name|e
operator|.
name|getActionCommand
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|BasePanel
name|toRaise
init|=
literal|null
decl_stmt|;
name|int
name|initialCount
init|=
name|filesToOpen
operator|.
name|size
argument_list|()
decl_stmt|,
name|removed
init|=
literal|0
decl_stmt|;
comment|// Check if any of the files are already open:
for|for
control|(
name|Iterator
argument_list|<
name|File
argument_list|>
name|iterator
init|=
name|filesToOpen
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|File
name|file
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BasePanel
name|bp
init|=
operator|(
name|BasePanel
operator|)
name|frame
operator|.
name|baseAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|bp
operator|.
name|getFile
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
name|bp
operator|.
name|getFile
argument_list|()
operator|.
name|equals
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|iterator
operator|.
name|remove
argument_list|()
expr_stmt|;
name|removed
operator|++
expr_stmt|;
comment|// See if we removed the final one. If so, we must perhaps
comment|// raise the BasePanel in question:
if|if
condition|(
name|removed
operator|==
name|initialCount
condition|)
block|{
name|toRaise
operator|=
name|bp
expr_stmt|;
block|}
break|break;
block|}
block|}
block|}
comment|// Run the actual open in a thread to prevent the program
comment|// locking until the file is loaded.
if|if
condition|(
name|filesToOpen
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|theFiles
init|=
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|filesToOpen
argument_list|)
decl_stmt|;
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
for|for
control|(
name|Iterator
argument_list|<
name|File
argument_list|>
name|i
init|=
name|theFiles
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|openIt
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|File
argument_list|>
name|i
init|=
name|theFiles
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
name|frame
operator|.
name|getFileHistory
argument_list|()
operator|.
name|newFile
argument_list|(
name|i
operator|.
name|next
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// If no files are remaining to open, this could mean that a file was
comment|// already open. If so, we may have to raise the correct tab:
elseif|else
if|if
condition|(
name|toRaise
operator|!=
literal|null
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"File '%0' is already open."
argument_list|,
name|toRaise
operator|.
name|getFile
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|toRaise
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|OpenItSwingHelper
class|class
name|OpenItSwingHelper
implements|implements
name|Runnable
block|{
DECL|field|bp
name|BasePanel
name|bp
decl_stmt|;
DECL|field|raisePanel
name|boolean
name|raisePanel
decl_stmt|;
DECL|field|file
name|File
name|file
decl_stmt|;
DECL|method|OpenItSwingHelper (BasePanel bp, File file, boolean raisePanel)
name|OpenItSwingHelper
parameter_list|(
name|BasePanel
name|bp
parameter_list|,
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|this
operator|.
name|bp
operator|=
name|bp
expr_stmt|;
name|this
operator|.
name|raisePanel
operator|=
name|raisePanel
expr_stmt|;
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|frame
operator|.
name|addTab
argument_list|(
name|bp
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openIt (File file, boolean raisePanel)
specifier|public
name|void
name|openIt
parameter_list|(
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
if|if
condition|(
operator|(
name|file
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|file
operator|.
name|exists
argument_list|()
operator|)
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Opening"
argument_list|)
operator|+
literal|": '"
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
try|try
block|{
name|String
name|fileName
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"workingDirectory"
argument_list|,
name|file
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
comment|// Should this be done _after_ we know it was successfully opened?
name|String
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|==
literal|null
operator|)
operator|||
operator|(
name|pr
operator|==
name|ParserResult
operator|.
name|INVALID_FORMAT
operator|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error opening file"
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"'"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|BasePanel
name|panel
init|=
name|addNewDatabase
argument_list|(
name|pr
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
decl_stmt|;
comment|// After adding the database, go through our list and see if
comment|// any post open actions need to be done. For instance, checking
comment|// if we found new entry types that can be imported, or checking
comment|// if the database contents should be modified due to new features
comment|// in this version of JabRef:
name|performPostOpenActions
argument_list|(
name|panel
argument_list|,
name|pr
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
name|Util
operator|.
name|showQuickErrorDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Open database"
argument_list|)
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Go through the list of post open actions, and perform those that need      * to be performed.      * @param panel The BasePanel where the database is shown.      * @param pr The result of the bib file parse operation.      */
DECL|method|performPostOpenActions (BasePanel panel, ParserResult pr, boolean mustRaisePanel)
specifier|public
specifier|static
name|void
name|performPostOpenActions
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|,
name|boolean
name|mustRaisePanel
parameter_list|)
block|{
for|for
control|(
name|Iterator
argument_list|<
name|PostOpenAction
argument_list|>
name|iterator
init|=
name|postOpenActions
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|PostOpenAction
name|action
init|=
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|action
operator|.
name|isActionNecessary
argument_list|(
name|pr
argument_list|)
condition|)
block|{
if|if
condition|(
name|mustRaisePanel
condition|)
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|setSelectedComponent
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|action
operator|.
name|performAction
argument_list|(
name|panel
argument_list|,
name|pr
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|addNewDatabase (ParserResult pr, File file, boolean raisePanel)
specifier|public
name|BasePanel
name|addNewDatabase
parameter_list|(
name|ParserResult
name|pr
parameter_list|,
name|File
name|file
parameter_list|,
name|boolean
name|raisePanel
parameter_list|)
block|{
name|String
name|fileName
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|BibtexDatabase
name|db
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|meta
init|=
name|pr
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
if|if
condition|(
name|pr
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
specifier|final
name|String
index|[]
name|wrns
init|=
name|pr
operator|.
name|warnings
argument_list|()
decl_stmt|;
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|StringBuffer
name|wrn
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|wrns
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|wrn
operator|.
name|append
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
operator|.
name|append
argument_list|(
name|wrns
index|[
name|i
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
if|if
condition|(
name|wrn
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|wrn
operator|.
name|deleteCharAt
argument_list|(
name|wrn
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
comment|// Note to self or to someone else: The following line causes an
comment|// ArrayIndexOutOfBoundsException in situations with a large number of
comment|// warnings; approx. 5000 for the database I opened when I observed the problem
comment|// (duplicate key warnings). I don't think this is a big problem for normal situations,
comment|// and it may possibly be a bug in the Swing code.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|wrn
operator|.
name|toString
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Warnings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
name|BasePanel
name|bp
init|=
operator|new
name|BasePanel
argument_list|(
name|frame
argument_list|,
name|db
argument_list|,
name|file
argument_list|,
name|meta
argument_list|,
name|pr
operator|.
name|getEncoding
argument_list|()
argument_list|)
decl_stmt|;
comment|// file is set to null inside the EventDispatcherThread
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|OpenItSwingHelper
argument_list|(
name|bp
argument_list|,
name|file
argument_list|,
name|raisePanel
argument_list|)
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Opened database"
argument_list|)
operator|+
literal|" '"
operator|+
name|fileName
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"with"
argument_list|)
operator|+
literal|" "
operator|+
name|db
operator|.
name|getEntryCount
argument_list|()
operator|+
literal|" "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"entries"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
name|bp
return|;
block|}
DECL|method|loadDatabase (File fileToOpen, String encoding)
specifier|public
specifier|static
name|ParserResult
name|loadDatabase
parameter_list|(
name|File
name|fileToOpen
parameter_list|,
name|String
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
comment|// First we make a quick check to see if this looks like a BibTeX file:
name|Reader
name|reader
decl_stmt|;
comment|// = ImportFormatReader.getReader(fileToOpen, encoding);
comment|//if (!BibtexParser.isRecognizedFormat(reader))
comment|//    return null;
comment|// The file looks promising. Reinitialize the reader and go on:
comment|//reader = getReader(fileToOpen, encoding);
comment|// We want to check if there is a JabRef signature in the file, because that would tell us
comment|// which character encoding is used. However, to read the signature we must be using a compatible
comment|// encoding in the first place. Since the signature doesn't contain any fancy characters, we can
comment|// read it regardless of encoding, with either UTF8 or UTF-16. That's the hypothesis, at any rate.
comment|// 8 bit is most likely, so we try that first:
name|Reader
name|utf8Reader
init|=
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
literal|"UTF8"
argument_list|)
decl_stmt|;
name|String
name|suppliedEncoding
init|=
name|checkForEncoding
argument_list|(
name|utf8Reader
argument_list|)
decl_stmt|;
name|utf8Reader
operator|.
name|close
argument_list|()
expr_stmt|;
comment|// Now if that didn't get us anywhere, we check with the 16 bit encoding:
if|if
condition|(
name|suppliedEncoding
operator|==
literal|null
condition|)
block|{
name|Reader
name|utf16Reader
init|=
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
literal|"UTF-16"
argument_list|)
decl_stmt|;
name|suppliedEncoding
operator|=
name|checkForEncoding
argument_list|(
name|utf16Reader
argument_list|)
expr_stmt|;
name|utf16Reader
operator|.
name|close
argument_list|()
expr_stmt|;
comment|//System.out.println("Result of UTF-16 test: "+suppliedEncoding);
block|}
comment|//System.out.println(suppliedEncoding != null ? "Encoding: '"+suppliedEncoding+"' Len: "+suppliedEncoding.length() : "no supplied encoding");
if|if
condition|(
operator|(
name|suppliedEncoding
operator|!=
literal|null
operator|)
condition|)
block|{
try|try
block|{
name|reader
operator|=
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|suppliedEncoding
argument_list|)
expr_stmt|;
name|encoding
operator|=
name|suppliedEncoding
expr_stmt|;
comment|// Just so we put the right info into the ParserResult.
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|reader
operator|=
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
comment|// The supplied encoding didn't work out, so we use the default.
block|}
block|}
else|else
block|{
comment|// We couldn't find a header with info about encoding. Use default:
name|reader
operator|=
name|ImportFormatReader
operator|.
name|getReader
argument_list|(
name|fileToOpen
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
name|BibtexParser
name|bp
init|=
operator|new
name|BibtexParser
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|bp
operator|.
name|parse
argument_list|()
decl_stmt|;
name|pr
operator|.
name|setEncoding
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
DECL|method|checkForEncoding (Reader reader)
specifier|private
specifier|static
name|String
name|checkForEncoding
parameter_list|(
name|Reader
name|reader
parameter_list|)
block|{
name|String
name|suppliedEncoding
init|=
literal|null
decl_stmt|;
name|StringBuffer
name|headerText
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
try|try
block|{
name|boolean
name|keepon
init|=
literal|true
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|,
name|offset
init|=
literal|0
decl_stmt|;
name|int
name|c
decl_stmt|;
while|while
condition|(
name|keepon
condition|)
block|{
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
expr_stmt|;
if|if
condition|(
operator|(
name|piv
operator|==
literal|0
operator|)
operator|&&
operator|(
operator|(
name|c
operator|==
literal|'%'
operator|)
operator|||
operator|(
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|)
operator|)
condition|)
name|offset
operator|++
expr_stmt|;
else|else
block|{
name|headerText
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
name|GUIGlobals
operator|.
name|SIGNATURE
operator|.
name|charAt
argument_list|(
name|piv
argument_list|)
condition|)
name|piv
operator|++
expr_stmt|;
else|else
comment|//if (((char)c) == '@')
name|keepon
operator|=
literal|false
expr_stmt|;
block|}
comment|//System.out.println(headerText.toString());
name|found
label|:
if|if
condition|(
name|piv
operator|==
name|GUIGlobals
operator|.
name|SIGNATURE
operator|.
name|length
argument_list|()
condition|)
block|{
name|keepon
operator|=
literal|false
expr_stmt|;
comment|//if (headerText.length()> GUIGlobals.SIGNATURE.length())
comment|//    System.out.println("'"+headerText.toString().substring(0, headerText.length()-GUIGlobals.SIGNATURE.length())+"'");
comment|// Found the signature. The rest of the line is unknown, so we skip
comment|// it:
while|while
condition|(
name|reader
operator|.
name|read
argument_list|()
operator|!=
literal|'\n'
condition|)
empty_stmt|;
comment|// If the next line starts with something like "% ", handle this:
while|while
condition|(
operator|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|==
literal|'%'
operator|)
operator|||
operator|(
name|Character
operator|.
name|isWhitespace
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
operator|)
condition|)
empty_stmt|;
comment|// Then we must skip the "Encoding: ". We may already have read the first
comment|// character:
if|if
condition|(
operator|(
name|char
operator|)
name|c
operator|!=
name|GUIGlobals
operator|.
name|encPrefix
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
condition|)
break|break
name|found
break|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|GUIGlobals
operator|.
name|encPrefix
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|reader
operator|.
name|read
argument_list|()
operator|!=
name|GUIGlobals
operator|.
name|encPrefix
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
condition|)
break|break
name|found
break|;
comment|// No,
comment|// it
comment|// doesn't
comment|// seem
comment|// to
comment|// match.
block|}
comment|// If ok, then read the rest of the line, which should contain the
comment|// name
comment|// of the encoding:
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|c
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
literal|'\n'
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
name|suppliedEncoding
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{         }
return|return
name|suppliedEncoding
operator|!=
literal|null
condition|?
name|suppliedEncoding
operator|.
name|trim
argument_list|()
else|:
literal|null
return|;
block|}
block|}
end_class

end_unit

