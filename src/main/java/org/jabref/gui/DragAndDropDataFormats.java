begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|DataFormat
import|;
end_import

begin_comment
comment|/**  * Contains all the different {@link DataFormat}s that may occur in JabRef.  */
end_comment

begin_class
DECL|class|DragAndDropDataFormats
specifier|public
class|class
name|DragAndDropDataFormats
block|{
DECL|field|GROUP
specifier|public
specifier|static
specifier|final
name|DataFormat
name|GROUP
init|=
operator|new
name|DataFormat
argument_list|(
literal|"dnd/org.jabref.model.groups.GroupTreeNode"
argument_list|)
decl_stmt|;
DECL|field|ENTRIES
specifier|public
specifier|static
specifier|final
name|DataFormat
name|ENTRIES
init|=
operator|new
name|DataFormat
argument_list|(
literal|"application/x-java-jvm-local-objectref"
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

