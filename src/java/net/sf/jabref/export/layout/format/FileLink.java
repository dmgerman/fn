begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|GUIGlobals
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
name|Globals
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
name|Util
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
name|export
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
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
name|FileListEntry
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
name|FileListTableModel
import|;
end_import

begin_comment
comment|/**  * Export formatter that handles the file link list of JabRef 2.3 and later, by  * selecting the first file link, if any, specified by the field.  */
end_comment

begin_class
DECL|class|FileLink
specifier|public
class|class
name|FileLink
implements|implements
name|ParamLayoutFormatter
block|{
DECL|field|fileType
name|String
name|fileType
init|=
literal|null
decl_stmt|;
DECL|method|format (String field)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|FileListTableModel
name|tableModel
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
if|if
condition|(
name|field
operator|==
literal|null
condition|)
return|return
literal|""
return|;
name|tableModel
operator|.
name|setContent
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|String
name|link
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|fileType
operator|==
literal|null
condition|)
block|{
comment|// No file type specified. Simply take the first link.
if|if
condition|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
name|link
operator|=
name|tableModel
operator|.
name|getEntry
argument_list|(
literal|0
argument_list|)
operator|.
name|getLink
argument_list|()
expr_stmt|;
else|else
name|link
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
comment|// A file type is specified:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tableModel
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
name|fileType
argument_list|)
condition|)
block|{
name|link
operator|=
name|flEntry
operator|.
name|getLink
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|link
operator|==
literal|null
condition|)
return|return
literal|""
return|;
comment|// Search in the standard file directory:
comment|/* TODO: oops, this part is not sufficient. We need access to the          database's metadata in order to check if the database overrides          the standard file directory */
name|String
name|dir
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
decl_stmt|;
name|File
name|f
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|link
argument_list|,
operator|new
name|String
index|[]
block|{
name|dir
block|,
literal|"."
block|}
argument_list|)
decl_stmt|;
comment|/* 		 * Stumbled over this while investigating 		 * 		 * https://sourceforge.net/tracker/index.php?func=detail&aid=1469903&group_id=92314&atid=600306 		 */
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
return|return
name|f
operator|.
name|getPath
argument_list|()
return|;
comment|//f.toURI().toString();
block|}
else|else
block|{
return|return
name|link
return|;
block|}
block|}
comment|/**      * This method is called if the layout file specifies an argument for this      * formatter. We use it as an indicator of which file type we should look for.      * @param arg The file type.      */
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|fileType
operator|=
name|arg
expr_stmt|;
block|}
block|}
end_class

end_unit

