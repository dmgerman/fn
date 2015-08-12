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
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
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
name|GUIGlobals
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

begin_comment
comment|/**  * Wider representation of icons  */
end_comment

begin_class
DECL|class|RankExtended
specifier|public
class|class
name|RankExtended
extends|extends
name|Rank
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|RankExtended
name|INSTANCE
decl_stmt|;
DECL|method|RankExtended ()
specifier|private
name|RankExtended
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|ArrayList
argument_list|<
name|SpecialFieldValue
argument_list|>
name|values
init|=
operator|new
name|ArrayList
argument_list|<
name|SpecialFieldValue
argument_list|>
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
literal|"clearRank"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear rank"
argument_list|)
argument_list|,
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No rank information"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// DO NOT TRANSLATE "rank1" etc. as this makes the .bib files non portable
name|values
operator|.
name|add
argument_list|(
operator|new
name|SpecialFieldValue
argument_list|(
name|this
argument_list|,
literal|"rank1"
argument_list|,
literal|"setRank1"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to one star"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"rank1"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"One star"
argument_list|)
argument_list|)
argument_list|)
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
literal|"rank2"
argument_list|,
literal|"setRank2"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to two stars"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"rank2"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Two stars"
argument_list|)
argument_list|)
argument_list|)
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
literal|"rank3"
argument_list|,
literal|"setRank3"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to three stars"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"rank3"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Three stars"
argument_list|)
argument_list|)
argument_list|)
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
literal|"rank4"
argument_list|,
literal|"setRank4"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to four stars"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"rank4"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Four stars"
argument_list|)
argument_list|)
argument_list|)
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
literal|"rank5"
argument_list|,
literal|"setRank5"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set rank to five stars"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"rank5"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Five stars"
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
name|RankExtended
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|RankExtended
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|RankExtended
operator|.
name|INSTANCE
operator|=
operator|new
name|RankExtended
argument_list|()
expr_stmt|;
block|}
return|return
name|RankExtended
operator|.
name|INSTANCE
return|;
block|}
annotation|@
name|Override
DECL|method|getRepresentingIcon ()
specifier|public
name|ImageIcon
name|getRepresentingIcon
parameter_list|()
block|{
return|return
name|this
operator|.
name|getValues
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|getIcon
argument_list|()
return|;
block|}
block|}
end_class

end_unit

