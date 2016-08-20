begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
package|;
end_package

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
name|javax
operator|.
name|swing
operator|.
name|Icon
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
name|IconTheme
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|entry
operator|.
name|SpecialFields
import|;
end_import

begin_class
DECL|class|ReadStatus
specifier|public
class|class
name|ReadStatus
extends|extends
name|SpecialField
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|ReadStatus
name|INSTANCE
decl_stmt|;
DECL|field|icon
specifier|private
specifier|final
name|Icon
name|icon
init|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|READ_STATUS
operator|.
name|getSmallIcon
argument_list|()
decl_stmt|;
DECL|method|ReadStatus ()
specifier|private
name|ReadStatus
parameter_list|()
block|{
name|List
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|null
argument_list|,
literal|"clearReadStatus"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear read status"
argument_list|)
argument_list|,
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No read status information"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Icon
name|tmpicon
decl_stmt|;
name|tmpicon
operator|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|READ_STATUS_READ
operator|.
name|getSmallIcon
argument_list|()
expr_stmt|;
comment|// DO NOT TRANSLATE "read" as this makes the produced .bib files non portable
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"read"
argument_list|,
literal|"setReadStatusToRead"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to read"
argument_list|)
argument_list|,
name|tmpicon
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status read"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|tmpicon
operator|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|READ_STATUS_SKIMMED
operator|.
name|getSmallIcon
argument_list|()
expr_stmt|;
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"skimmed"
argument_list|,
literal|"setReadStatusToSkimmed"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to skimmed"
argument_list|)
argument_list|,
name|tmpicon
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status skimmed"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setValues
argument_list|(
name|values
argument_list|)
expr_stmt|;
block|}
DECL|method|getInstance ()
specifier|public
specifier|static
name|ReadStatus
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|ReadStatus
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|ReadStatus
operator|.
name|INSTANCE
operator|=
operator|new
name|ReadStatus
argument_list|()
expr_stmt|;
block|}
return|return
name|ReadStatus
operator|.
name|INSTANCE
return|;
block|}
annotation|@
name|Override
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|SpecialFields
operator|.
name|FIELDNAME_READ
return|;
block|}
annotation|@
name|Override
DECL|method|getLocalizedFieldName ()
specifier|public
name|String
name|getLocalizedFieldName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRepresentingIcon ()
specifier|public
name|Icon
name|getRepresentingIcon
parameter_list|()
block|{
return|return
name|this
operator|.
name|icon
return|;
block|}
block|}
end_class

end_unit

