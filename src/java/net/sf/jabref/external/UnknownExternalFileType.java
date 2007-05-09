begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|GUIGlobals
import|;
end_import

begin_comment
comment|/**  * This subclass of ExternalFileType is used to mark types that are unknown.  * This can be the case when a database is loaded which contains links to files  * of a type that has not been defined on this JabRef instance.  */
end_comment

begin_class
DECL|class|UnknownExternalFileType
specifier|public
class|class
name|UnknownExternalFileType
extends|extends
name|ExternalFileType
block|{
DECL|method|UnknownExternalFileType (String name)
specifier|public
name|UnknownExternalFileType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"unknown"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

