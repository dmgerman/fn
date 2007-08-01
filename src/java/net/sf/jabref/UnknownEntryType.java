begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_comment
comment|/**  * This class is used to represent an unknown entry type, e.g. encountered  * during bibtex parsing. The only known information is the type name.  * This is useful if the bibtex file contains type definitions that are used  * in the file - because the entries will be parsed before the type definitions  * are found. In the meantime, the entries will be assigned an   * UnknownEntryType giving the name.  */
end_comment

begin_class
DECL|class|UnknownEntryType
specifier|public
class|class
name|UnknownEntryType
extends|extends
name|BibtexEntryType
block|{
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
init|=
operator|new
name|String
index|[
literal|0
index|]
decl_stmt|;
DECL|method|UnknownEntryType (String name_)
specifier|public
name|UnknownEntryType
parameter_list|(
name|String
name|name_
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|fields
return|;
block|}
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|fields
return|;
block|}
DECL|method|describeRequiredFields ()
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"unknown"
return|;
block|}
DECL|method|describeOptionalFields ()
specifier|public
name|String
name|describeOptionalFields
parameter_list|()
block|{
return|return
literal|"unknown"
return|;
block|}
DECL|method|hasAllRequiredFields (BibtexEntry entry)
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
DECL|method|save (Writer out)
specifier|public
name|void
name|save
parameter_list|(
name|Writer
name|out
parameter_list|)
block|{     }
block|}
end_class

end_unit

