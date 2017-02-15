begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|Objects
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
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|specialfields
operator|.
name|SpecialFieldValue
import|;
end_import

begin_class
DECL|class|SpecialFieldValueViewModel
specifier|public
class|class
name|SpecialFieldValueViewModel
block|{
DECL|field|value
specifier|private
specifier|final
name|SpecialFieldValue
name|value
decl_stmt|;
DECL|method|SpecialFieldValueViewModel (SpecialFieldValue value)
specifier|public
name|SpecialFieldValueViewModel
parameter_list|(
name|SpecialFieldValue
name|value
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
DECL|method|getSpecialFieldValueIcon ()
specifier|public
name|Icon
name|getSpecialFieldValueIcon
parameter_list|()
block|{
switch|switch
condition|(
name|value
condition|)
block|{
case|case
name|PRINTED
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRINTED
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|CLEAR_PRIORITY
case|:
return|return
literal|null
return|;
case|case
name|PRIORITY_HIGH
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRIORITY_HIGH
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|PRIORITY_MEDIUM
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRIORITY_MEDIUM
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|PRIORITY_LOW
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRIORITY_LOW
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|QUALITY_ASSURED
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|QUALITY_ASSURED
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|CLEAR_RANK
case|:
return|return
literal|null
return|;
case|case
name|RANK_1
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RANK1
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|RANK_2
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RANK2
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|RANK_3
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RANK3
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|RANK_4
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RANK4
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|RANK_5
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RANK5
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|CLEAR_READ_STATUS
case|:
return|return
literal|null
return|;
case|case
name|READ
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|READ_STATUS_READ
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|SKIMMED
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|READ_STATUS_SKIMMED
operator|.
name|getSmallIcon
argument_list|()
return|;
case|case
name|RELEVANT
case|:
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|RELEVANCE
operator|.
name|getSmallIcon
argument_list|()
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"There is no icon mapping for special field value "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
DECL|method|createSpecialFieldValueLabel ()
specifier|public
name|JLabel
name|createSpecialFieldValueLabel
parameter_list|()
block|{
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|getSpecialFieldValueIcon
argument_list|()
argument_list|)
decl_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
DECL|method|getMenuString ()
specifier|public
name|String
name|getMenuString
parameter_list|()
block|{
switch|switch
condition|(
name|value
condition|)
block|{
case|case
name|PRINTED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle print status"
argument_list|)
return|;
case|case
name|CLEAR_PRIORITY
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear priority"
argument_list|)
return|;
case|case
name|PRIORITY_HIGH
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to high"
argument_list|)
return|;
case|case
name|PRIORITY_MEDIUM
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to medium"
argument_list|)
return|;
case|case
name|PRIORITY_LOW
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set priority to low"
argument_list|)
return|;
case|case
name|QUALITY_ASSURED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle quality assured"
argument_list|)
return|;
case|case
name|CLEAR_RANK
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear rank"
argument_list|)
return|;
case|case
name|RANK_1
case|:
return|return
literal|""
return|;
case|case
name|RANK_2
case|:
return|return
literal|""
return|;
case|case
name|RANK_3
case|:
return|return
literal|""
return|;
case|case
name|RANK_4
case|:
return|return
literal|""
return|;
case|case
name|RANK_5
case|:
return|return
literal|""
return|;
case|case
name|CLEAR_READ_STATUS
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear read status"
argument_list|)
return|;
case|case
name|READ
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to read"
argument_list|)
return|;
case|case
name|SKIMMED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set read status to skimmed"
argument_list|)
return|;
case|case
name|RELEVANT
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle relevance"
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"There is no tooltip localization for special field value "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
DECL|method|getToolTipText ()
specifier|public
name|String
name|getToolTipText
parameter_list|()
block|{
switch|switch
condition|(
name|value
condition|)
block|{
case|case
name|PRINTED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle print status"
argument_list|)
return|;
case|case
name|CLEAR_PRIORITY
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"No priority information"
argument_list|)
return|;
case|case
name|PRIORITY_HIGH
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Priority high"
argument_list|)
return|;
case|case
name|PRIORITY_MEDIUM
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Priority medium"
argument_list|)
return|;
case|case
name|PRIORITY_LOW
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Priority low"
argument_list|)
return|;
case|case
name|QUALITY_ASSURED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle quality assured"
argument_list|)
return|;
case|case
name|CLEAR_RANK
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"No rank information"
argument_list|)
return|;
case|case
name|RANK_1
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"One star"
argument_list|)
return|;
case|case
name|RANK_2
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Two stars"
argument_list|)
return|;
case|case
name|RANK_3
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Three stars"
argument_list|)
return|;
case|case
name|RANK_4
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Four stars"
argument_list|)
return|;
case|case
name|RANK_5
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Five stars"
argument_list|)
return|;
case|case
name|CLEAR_READ_STATUS
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"No read status information"
argument_list|)
return|;
case|case
name|READ
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status read"
argument_list|)
return|;
case|case
name|SKIMMED
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Read status skimmed"
argument_list|)
return|;
case|case
name|RELEVANT
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle relevance"
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"There is no tooltip localization for special field value "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
DECL|method|getActionName ()
specifier|public
name|String
name|getActionName
parameter_list|()
block|{
switch|switch
condition|(
name|value
condition|)
block|{
case|case
name|PRINTED
case|:
return|return
literal|"togglePrinted"
return|;
case|case
name|CLEAR_PRIORITY
case|:
return|return
literal|"clearPriority"
return|;
case|case
name|PRIORITY_HIGH
case|:
return|return
literal|"setPriority1"
return|;
case|case
name|PRIORITY_MEDIUM
case|:
return|return
literal|"setPriority2"
return|;
case|case
name|PRIORITY_LOW
case|:
return|return
literal|"setPriority3"
return|;
case|case
name|QUALITY_ASSURED
case|:
return|return
literal|"toggleQualityAssured"
return|;
case|case
name|CLEAR_RANK
case|:
return|return
literal|"clearRank"
return|;
case|case
name|RANK_1
case|:
return|return
literal|"setRank1"
return|;
case|case
name|RANK_2
case|:
return|return
literal|"setRank2"
return|;
case|case
name|RANK_3
case|:
return|return
literal|"setRank3"
return|;
case|case
name|RANK_4
case|:
return|return
literal|"setRank4"
return|;
case|case
name|RANK_5
case|:
return|return
literal|"setRank5"
return|;
case|case
name|CLEAR_READ_STATUS
case|:
return|return
literal|"clearReadStatus"
return|;
case|case
name|READ
case|:
return|return
literal|"setReadStatusToRead"
return|;
case|case
name|SKIMMED
case|:
return|return
literal|"setReadStatusToSkimmed"
return|;
case|case
name|RELEVANT
case|:
return|return
literal|"toggleRelevance"
return|;
default|default:
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"There is no action name for special field value "
operator|+
name|value
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit
