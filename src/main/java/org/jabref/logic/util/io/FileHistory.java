begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.io
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
package|;
end_package

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
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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

begin_class
DECL|class|FileHistory
specifier|public
class|class
name|FileHistory
block|{
DECL|field|HISTORY_SIZE
specifier|private
specifier|static
specifier|final
name|int
name|HISTORY_SIZE
init|=
literal|8
decl_stmt|;
DECL|field|history
specifier|private
specifier|final
name|LinkedList
argument_list|<
name|Path
argument_list|>
name|history
decl_stmt|;
DECL|method|FileHistory (List<Path> files)
specifier|public
name|FileHistory
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
name|history
operator|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|files
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|size ()
specifier|public
name|int
name|size
parameter_list|()
block|{
return|return
name|history
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|isEmpty ()
specifier|public
name|boolean
name|isEmpty
parameter_list|()
block|{
return|return
name|history
operator|.
name|isEmpty
argument_list|()
return|;
block|}
comment|/**      * Adds the file to the top of the list. If it already is in the list, it is merely moved to the top.      */
DECL|method|newFile (Path file)
specifier|public
name|void
name|newFile
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
name|removeItem
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|history
operator|.
name|addFirst
argument_list|(
name|file
argument_list|)
expr_stmt|;
while|while
condition|(
name|size
argument_list|()
operator|>
name|HISTORY_SIZE
condition|)
block|{
name|history
operator|.
name|removeLast
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getFileAt (int index)
specifier|public
name|Path
name|getFileAt
parameter_list|(
name|int
name|index
parameter_list|)
block|{
return|return
name|history
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
block|}
DECL|method|removeItem (Path file)
specifier|public
name|void
name|removeItem
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
name|history
operator|.
name|remove
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
DECL|method|getHistory ()
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|getHistory
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|history
argument_list|)
return|;
block|}
block|}
end_class

end_unit

