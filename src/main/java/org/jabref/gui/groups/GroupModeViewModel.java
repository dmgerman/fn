begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tooltip
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|icon
operator|.
name|IconTheme
operator|.
name|JabRefIcons
import|;
end_import

begin_import
import|import
name|org
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

begin_class
DECL|class|GroupModeViewModel
specifier|public
class|class
name|GroupModeViewModel
block|{
DECL|field|mode
specifier|private
specifier|final
name|GroupViewMode
name|mode
decl_stmt|;
DECL|method|GroupModeViewModel (GroupViewMode mode)
specifier|public
name|GroupModeViewModel
parameter_list|(
name|GroupViewMode
name|mode
parameter_list|)
block|{
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
block|}
DECL|method|getUnionIntersectionGraphic ()
specifier|public
name|Node
name|getUnionIntersectionGraphic
parameter_list|()
block|{
if|if
condition|(
name|mode
operator|==
name|GroupViewMode
operator|.
name|UNION
condition|)
block|{
return|return
name|JabRefIcons
operator|.
name|GROUP_UNION
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|mode
operator|==
name|GroupViewMode
operator|.
name|INTERSECTION
condition|)
block|{
return|return
name|JabRefIcons
operator|.
name|GROUP_INTERSECTION
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
comment|// As there is no concept like an empty node/icon, we return simply the other icon
return|return
name|JabRefIcons
operator|.
name|GROUP_INTERSECTION
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
DECL|method|getUnionIntersectionTooltip ()
specifier|public
name|Tooltip
name|getUnionIntersectionTooltip
parameter_list|()
block|{
if|if
condition|(
name|mode
operator|==
name|GroupViewMode
operator|.
name|UNION
condition|)
block|{
return|return
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle intersection"
argument_list|)
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|mode
operator|==
name|GroupViewMode
operator|.
name|INTERSECTION
condition|)
block|{
return|return
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Toggle union"
argument_list|)
argument_list|)
return|;
block|}
return|return
operator|new
name|Tooltip
argument_list|()
return|;
block|}
block|}
end_class

end_unit

