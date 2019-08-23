begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|entry
operator|.
name|EntryTypeFactory
import|;
end_import

begin_comment
comment|/*  * Camel casing of entry type string, unknown entry types gets a leading capital  *  * Example (known): inbook -> InBook  * Example (unknown): banana -> Banana  */
end_comment

begin_class
DECL|class|EntryTypeFormatter
specifier|public
class|class
name|EntryTypeFormatter
implements|implements
name|LayoutFormatter
block|{
comment|/**      * Input: entry type as a string      */
annotation|@
name|Override
DECL|method|format (String entryType)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|entryType
parameter_list|)
block|{
return|return
name|EntryTypeFactory
operator|.
name|parse
argument_list|(
name|entryType
argument_list|)
operator|.
name|getDisplayName
argument_list|()
return|;
block|}
block|}
end_class

end_unit

