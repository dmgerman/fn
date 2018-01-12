begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

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
name|nio
operator|.
name|charset
operator|.
name|Charset
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|FieldChange
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|SaveSession
specifier|public
specifier|abstract
class|class
name|SaveSession
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SaveSession
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|backup
specifier|protected
name|boolean
name|backup
decl_stmt|;
DECL|field|encoding
specifier|protected
specifier|final
name|Charset
name|encoding
decl_stmt|;
DECL|field|writer
specifier|protected
specifier|final
name|VerifyingWriter
name|writer
decl_stmt|;
DECL|field|undoableFieldChanges
specifier|private
specifier|final
name|List
argument_list|<
name|FieldChange
argument_list|>
name|undoableFieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|SaveSession (Charset encoding, boolean backup, VerifyingWriter writer)
specifier|protected
name|SaveSession
parameter_list|(
name|Charset
name|encoding
parameter_list|,
name|boolean
name|backup
parameter_list|,
name|VerifyingWriter
name|writer
parameter_list|)
block|{
name|this
operator|.
name|encoding
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|encoding
argument_list|)
expr_stmt|;
name|this
operator|.
name|backup
operator|=
name|backup
expr_stmt|;
name|this
operator|.
name|writer
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|writer
argument_list|)
expr_stmt|;
block|}
DECL|method|getWriter ()
specifier|public
name|VerifyingWriter
name|getWriter
parameter_list|()
block|{
return|return
name|writer
return|;
block|}
DECL|method|getEncoding ()
specifier|public
name|Charset
name|getEncoding
parameter_list|()
block|{
return|return
name|encoding
return|;
block|}
DECL|method|setUseBackup (boolean useBackup)
specifier|public
name|void
name|setUseBackup
parameter_list|(
name|boolean
name|useBackup
parameter_list|)
block|{
name|this
operator|.
name|backup
operator|=
name|useBackup
expr_stmt|;
block|}
DECL|method|commit (Path file)
specifier|public
specifier|abstract
name|void
name|commit
parameter_list|(
name|Path
name|file
parameter_list|)
throws|throws
name|SaveException
function_decl|;
DECL|method|commit (String path)
specifier|public
name|void
name|commit
parameter_list|(
name|String
name|path
parameter_list|)
throws|throws
name|SaveException
block|{
name|commit
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|path
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|cancel ()
specifier|public
specifier|abstract
name|void
name|cancel
parameter_list|()
function_decl|;
DECL|method|getFieldChanges ()
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|getFieldChanges
parameter_list|()
block|{
return|return
name|undoableFieldChanges
return|;
block|}
DECL|method|addFieldChanges (List<FieldChange> newUndoableFieldChanges)
specifier|public
name|void
name|addFieldChanges
parameter_list|(
name|List
argument_list|<
name|FieldChange
argument_list|>
name|newUndoableFieldChanges
parameter_list|)
block|{
name|this
operator|.
name|undoableFieldChanges
operator|.
name|addAll
argument_list|(
name|newUndoableFieldChanges
argument_list|)
expr_stmt|;
block|}
DECL|method|finalize (Path file)
specifier|public
name|void
name|finalize
parameter_list|(
name|Path
name|file
parameter_list|)
throws|throws
name|SaveException
throws|,
name|IOException
block|{
name|getWriter
argument_list|()
operator|.
name|flush
argument_list|()
expr_stmt|;
name|getWriter
argument_list|()
operator|.
name|close
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|getWriter
argument_list|()
operator|.
name|couldEncodeAll
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not encode..."
argument_list|)
expr_stmt|;
block|}
name|commit
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

