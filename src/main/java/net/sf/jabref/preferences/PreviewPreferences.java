begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.preferences
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|preferences
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

begin_class
DECL|class|PreviewPreferences
specifier|public
class|class
name|PreviewPreferences
block|{
DECL|field|previewCycle
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|previewCycle
decl_stmt|;
DECL|field|previewCyclePosition
specifier|private
specifier|final
name|int
name|previewCyclePosition
decl_stmt|;
DECL|field|previewPanelHeight
specifier|private
specifier|final
name|int
name|previewPanelHeight
decl_stmt|;
DECL|field|previewPanelEnabled
specifier|private
specifier|final
name|boolean
name|previewPanelEnabled
decl_stmt|;
DECL|field|previewStyle
specifier|private
specifier|final
name|String
name|previewStyle
decl_stmt|;
DECL|field|previewStyleDefault
specifier|private
specifier|final
name|String
name|previewStyleDefault
decl_stmt|;
DECL|method|PreviewPreferences (List<String> previewCycle, int previeCyclePosition, int previewPanelHeight, boolean previewPanelEnabled, String previewStyle, String previewStyleDefault)
specifier|public
name|PreviewPreferences
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|previewCycle
parameter_list|,
name|int
name|previeCyclePosition
parameter_list|,
name|int
name|previewPanelHeight
parameter_list|,
name|boolean
name|previewPanelEnabled
parameter_list|,
name|String
name|previewStyle
parameter_list|,
name|String
name|previewStyleDefault
parameter_list|)
block|{
name|this
operator|.
name|previewCycle
operator|=
name|previewCycle
expr_stmt|;
name|this
operator|.
name|previewCyclePosition
operator|=
name|previeCyclePosition
expr_stmt|;
name|this
operator|.
name|previewPanelHeight
operator|=
name|previewPanelHeight
expr_stmt|;
name|this
operator|.
name|previewPanelEnabled
operator|=
name|previewPanelEnabled
expr_stmt|;
name|this
operator|.
name|previewStyle
operator|=
name|previewStyle
expr_stmt|;
name|this
operator|.
name|previewStyleDefault
operator|=
name|previewStyleDefault
expr_stmt|;
block|}
DECL|method|getPreviewCycle ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPreviewCycle
parameter_list|()
block|{
return|return
name|previewCycle
return|;
block|}
DECL|method|getPreviewCyclePosition ()
specifier|public
name|int
name|getPreviewCyclePosition
parameter_list|()
block|{
return|return
name|previewCyclePosition
return|;
block|}
DECL|method|getPreviewPanelHeight ()
specifier|public
name|int
name|getPreviewPanelHeight
parameter_list|()
block|{
return|return
name|previewPanelHeight
return|;
block|}
DECL|method|isPreviewPanelEnabled ()
specifier|public
name|boolean
name|isPreviewPanelEnabled
parameter_list|()
block|{
return|return
name|previewPanelEnabled
return|;
block|}
DECL|method|getPreviewStyle ()
specifier|public
name|String
name|getPreviewStyle
parameter_list|()
block|{
return|return
name|previewStyle
return|;
block|}
DECL|method|getPreviewStyleDefault ()
specifier|public
name|String
name|getPreviewStyleDefault
parameter_list|()
block|{
return|return
name|previewStyleDefault
return|;
block|}
DECL|method|getBuilder ()
specifier|public
name|Builder
name|getBuilder
parameter_list|()
block|{
return|return
operator|new
name|Builder
argument_list|(
name|this
argument_list|)
return|;
block|}
DECL|class|Builder
specifier|public
specifier|static
class|class
name|Builder
block|{
DECL|field|previewCycle
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|previewCycle
decl_stmt|;
DECL|field|previeCyclePosition
specifier|private
name|int
name|previeCyclePosition
decl_stmt|;
DECL|field|previewPanelHeight
specifier|private
name|int
name|previewPanelHeight
decl_stmt|;
DECL|field|previewPanelEnabled
specifier|private
name|boolean
name|previewPanelEnabled
decl_stmt|;
DECL|field|previewStyle
specifier|private
name|String
name|previewStyle
decl_stmt|;
DECL|field|previewStyleDefault
specifier|private
specifier|final
name|String
name|previewStyleDefault
decl_stmt|;
DECL|method|Builder (PreviewPreferences previewPreferences)
specifier|public
name|Builder
parameter_list|(
name|PreviewPreferences
name|previewPreferences
parameter_list|)
block|{
name|this
operator|.
name|previewCycle
operator|=
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
expr_stmt|;
name|this
operator|.
name|previeCyclePosition
operator|=
name|previewPreferences
operator|.
name|getPreviewCyclePosition
argument_list|()
expr_stmt|;
name|this
operator|.
name|previewPanelHeight
operator|=
name|previewPreferences
operator|.
name|getPreviewPanelHeight
argument_list|()
expr_stmt|;
name|this
operator|.
name|previewPanelEnabled
operator|=
name|previewPreferences
operator|.
name|isPreviewPanelEnabled
argument_list|()
expr_stmt|;
name|this
operator|.
name|previewStyle
operator|=
name|previewPreferences
operator|.
name|getPreviewStyle
argument_list|()
expr_stmt|;
name|this
operator|.
name|previewStyleDefault
operator|=
name|previewPreferences
operator|.
name|getPreviewStyleDefault
argument_list|()
expr_stmt|;
block|}
DECL|method|withPreviewCycle (List<String> previewCycle)
specifier|public
name|Builder
name|withPreviewCycle
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|previewCycle
parameter_list|)
block|{
name|this
operator|.
name|previewCycle
operator|=
name|previewCycle
expr_stmt|;
return|return
name|withPreviewCyclePosition
argument_list|(
name|previeCyclePosition
argument_list|)
return|;
block|}
DECL|method|withPreviewCyclePosition (int position)
specifier|public
name|Builder
name|withPreviewCyclePosition
parameter_list|(
name|int
name|position
parameter_list|)
block|{
name|previeCyclePosition
operator|=
name|position
expr_stmt|;
while|while
condition|(
name|previeCyclePosition
operator|<
literal|0
condition|)
block|{
name|previeCyclePosition
operator|+=
name|previewCycle
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
name|previeCyclePosition
operator|%=
name|previewCycle
operator|.
name|size
argument_list|()
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withPreviewPanelHeight (int previewPanelHeight)
specifier|public
name|Builder
name|withPreviewPanelHeight
parameter_list|(
name|int
name|previewPanelHeight
parameter_list|)
block|{
name|this
operator|.
name|previewPanelHeight
operator|=
name|previewPanelHeight
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withPreviewPanelEnabled (boolean previewPanelEnabled)
specifier|public
name|Builder
name|withPreviewPanelEnabled
parameter_list|(
name|boolean
name|previewPanelEnabled
parameter_list|)
block|{
name|this
operator|.
name|previewPanelEnabled
operator|=
name|previewPanelEnabled
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|withPreviewStyle (String previewStyle)
specifier|public
name|Builder
name|withPreviewStyle
parameter_list|(
name|String
name|previewStyle
parameter_list|)
block|{
name|this
operator|.
name|previewStyle
operator|=
name|previewStyle
expr_stmt|;
return|return
name|this
return|;
block|}
DECL|method|build ()
specifier|public
name|PreviewPreferences
name|build
parameter_list|()
block|{
return|return
operator|new
name|PreviewPreferences
argument_list|(
name|previewCycle
argument_list|,
name|previeCyclePosition
argument_list|,
name|previewPanelHeight
argument_list|,
name|previewPanelEnabled
argument_list|,
name|previewStyle
argument_list|,
name|previewStyleDefault
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit
