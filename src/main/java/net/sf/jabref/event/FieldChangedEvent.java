begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.event
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|event
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  *<code>FieldChangedEvent</code> is fired when a field of<code>BibEntry</code> has been modified, removed or added.  */
end_comment

begin_class
DECL|class|FieldChangedEvent
specifier|public
class|class
name|FieldChangedEvent
extends|extends
name|EntryChangedEvent
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|newValue
specifier|private
specifier|final
name|String
name|newValue
decl_stmt|;
comment|/**      * @param bibEntry Affected BibEntry object      * @param fieldName Name of field which has been changed      * @param newValue new field value      */
DECL|method|FieldChangedEvent (BibEntry bibEntry, String fieldName, String newValue)
specifier|public
name|FieldChangedEvent
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
name|super
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
DECL|method|getNewValue ()
specifier|public
name|String
name|getNewValue
parameter_list|()
block|{
return|return
name|newValue
return|;
block|}
block|}
end_class

end_unit

