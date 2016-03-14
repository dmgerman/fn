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

begin_class
DECL|class|HighlightMatchingGroupPreferences
specifier|public
class|class
name|HighlightMatchingGroupPreferences
block|{
DECL|field|ALL
specifier|public
specifier|static
specifier|final
name|String
name|ALL
init|=
literal|"all"
decl_stmt|;
DECL|field|ANY
specifier|public
specifier|static
specifier|final
name|String
name|ANY
init|=
literal|"any"
decl_stmt|;
DECL|field|DISABLED
specifier|public
specifier|static
specifier|final
name|String
name|DISABLED
init|=
literal|""
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|HighlightMatchingGroupPreferences (JabRefPreferences preferences)
specifier|public
name|HighlightMatchingGroupPreferences
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
DECL|method|isAll ()
specifier|public
name|boolean
name|isAll
parameter_list|()
block|{
return|return
name|ALL
operator|.
name|equals
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|HIGHLIGHT_GROUPS_MATCHING
argument_list|)
argument_list|)
return|;
block|}
DECL|method|isAny ()
specifier|public
name|boolean
name|isAny
parameter_list|()
block|{
return|return
name|ANY
operator|.
name|equals
argument_list|(
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|HIGHLIGHT_GROUPS_MATCHING
argument_list|)
argument_list|)
return|;
block|}
DECL|method|isDisabled ()
specifier|public
name|boolean
name|isDisabled
parameter_list|()
block|{
return|return
operator|!
name|isAll
argument_list|()
operator|&&
operator|!
name|isAny
argument_list|()
return|;
block|}
DECL|method|setToAll ()
specifier|public
name|void
name|setToAll
parameter_list|()
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|HIGHLIGHT_GROUPS_MATCHING
argument_list|,
name|ALL
argument_list|)
expr_stmt|;
block|}
DECL|method|setToAny ()
specifier|public
name|void
name|setToAny
parameter_list|()
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|HIGHLIGHT_GROUPS_MATCHING
argument_list|,
name|ANY
argument_list|)
expr_stmt|;
block|}
DECL|method|setToDisabled ()
specifier|public
name|void
name|setToDisabled
parameter_list|()
block|{
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|HIGHLIGHT_GROUPS_MATCHING
argument_list|,
name|DISABLED
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
