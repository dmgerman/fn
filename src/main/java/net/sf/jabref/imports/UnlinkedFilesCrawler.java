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
name|FileFilter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeListener
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|gui
operator|.
name|FindUnlinkedFilesDialog
operator|.
name|CheckableTreeNode
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
name|gui
operator|.
name|FindUnlinkedFilesDialog
operator|.
name|FileNodeWrapper
import|;
end_import

begin_comment
comment|/**  * Util class for searching files on the filessystem which are not linked to a  * provided {@link BibtexDatabase}.  *   * @author Nosh&Dan  * @version 09.11.2008 | 19:55:20  *   */
end_comment

begin_class
DECL|class|UnlinkedFilesCrawler
specifier|public
class|class
name|UnlinkedFilesCrawler
block|{
comment|/**      * File filter, that accepts directorys only.      */
DECL|field|directoryFilter
specifier|private
specifier|final
name|FileFilter
name|directoryFilter
init|=
operator|new
name|FileFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|pathname
parameter_list|)
block|{
if|if
condition|(
name|pathname
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|pathname
operator|.
name|isDirectory
argument_list|()
return|;
block|}
block|}
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibtexDatabase
name|database
decl_stmt|;
comment|/**      * CONSTRUCTOR      *       * @param database      */
DECL|method|UnlinkedFilesCrawler (BibtexDatabase database)
specifier|public
name|UnlinkedFilesCrawler
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
DECL|method|searchDirectory (File directory, FileFilter aFileFilter)
specifier|public
name|CheckableTreeNode
name|searchDirectory
parameter_list|(
name|File
name|directory
parameter_list|,
name|FileFilter
name|aFileFilter
parameter_list|)
block|{
name|UnlinkedPDFFileFilter
name|ff
init|=
operator|new
name|UnlinkedPDFFileFilter
argument_list|(
name|aFileFilter
argument_list|,
name|database
argument_list|)
decl_stmt|;
return|return
name|searchDirectory
argument_list|(
name|directory
argument_list|,
name|ff
argument_list|,
operator|new
name|int
index|[]
block|{
literal|1
block|}
argument_list|,
literal|null
argument_list|)
return|;
block|}
comment|/**      * Searches recursively all files in the specified directory.<br>      *<br>      * All {@link File}s, which match the {@link FileFilter} that comes with the      * {@link EntryFromFileCreatorManager}, are taken into the resulting tree.<br>      *<br>      * The result will be a tree structure of nodes of the type      * {@link CheckableTreeNode}.<br>      *<br>      * The user objects that are attached to the nodes is the      * {@link FileNodeWrapper}, which wrapps the {@link File}-Object.<br>      *<br>      * For ensuring the capability to cancel the work of this recursive method,      * the first position in the integer array 'state' must be set to 1, to keep      * the recursion running. When the states value changes, the methode will      * resolve its recursion and return what it has saved so far.      */
DECL|method|searchDirectory (File directory, UnlinkedPDFFileFilter ff, int[] state, ChangeListener changeListener)
specifier|public
name|CheckableTreeNode
name|searchDirectory
parameter_list|(
name|File
name|directory
parameter_list|,
name|UnlinkedPDFFileFilter
name|ff
parameter_list|,
name|int
index|[]
name|state
parameter_list|,
name|ChangeListener
name|changeListener
parameter_list|)
block|{
comment|/* Cancellation of the search from outside! */
if|if
condition|(
name|state
operator|==
literal|null
operator|||
name|state
operator|.
name|length
operator|<
literal|1
operator|||
name|state
index|[
literal|0
index|]
operator|!=
literal|1
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|/* Return null if the directory is not valid. */
if|if
condition|(
name|directory
operator|==
literal|null
operator|||
operator|!
name|directory
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|directory
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|File
index|[]
name|files
init|=
name|directory
operator|.
name|listFiles
argument_list|(
name|ff
argument_list|)
decl_stmt|;
name|CheckableTreeNode
name|root
init|=
operator|new
name|CheckableTreeNode
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|int
name|filesCount
init|=
literal|0
decl_stmt|;
name|File
index|[]
name|subDirectories
init|=
name|directory
operator|.
name|listFiles
argument_list|(
name|directoryFilter
argument_list|)
decl_stmt|;
for|for
control|(
name|File
name|subDirectory
range|:
name|subDirectories
control|)
block|{
name|CheckableTreeNode
name|subRoot
init|=
name|searchDirectory
argument_list|(
name|subDirectory
argument_list|,
name|ff
argument_list|,
name|state
argument_list|,
name|changeListener
argument_list|)
decl_stmt|;
if|if
condition|(
name|subRoot
operator|!=
literal|null
operator|&&
name|subRoot
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|filesCount
operator|+=
operator|(
operator|(
name|FileNodeWrapper
operator|)
name|subRoot
operator|.
name|getUserObject
argument_list|()
operator|)
operator|.
name|fileCount
expr_stmt|;
name|root
operator|.
name|add
argument_list|(
name|subRoot
argument_list|)
expr_stmt|;
block|}
block|}
name|root
operator|.
name|setUserObject
argument_list|(
operator|new
name|FileNodeWrapper
argument_list|(
name|directory
argument_list|,
name|files
operator|.
name|length
operator|+
name|filesCount
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
name|root
operator|.
name|add
argument_list|(
operator|new
name|CheckableTreeNode
argument_list|(
operator|new
name|FileNodeWrapper
argument_list|(
name|file
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|changeListener
operator|!=
literal|null
condition|)
block|{
name|changeListener
operator|.
name|stateChanged
argument_list|(
operator|new
name|ChangeEvent
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|root
return|;
block|}
block|}
end_class

end_unit

