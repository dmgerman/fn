begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

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
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * Interface for {@link StandardFileType} which allows us to extend the underlying enum with own filetypes for custom exporters  *  */
end_comment

begin_interface
DECL|interface|FileType
specifier|public
interface|interface
name|FileType
block|{
DECL|method|getExtensionsWithDot ()
specifier|default
name|List
argument_list|<
name|String
argument_list|>
name|getExtensionsWithDot
parameter_list|()
block|{
return|return
name|getExtensions
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|extension
lambda|->
literal|"*."
operator|+
name|extension
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getExtensions ()
name|List
argument_list|<
name|String
argument_list|>
name|getExtensions
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

