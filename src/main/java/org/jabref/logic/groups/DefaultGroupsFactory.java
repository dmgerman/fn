begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
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
name|l10n
operator|.
name|Localization
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
name|groups
operator|.
name|AllEntriesGroup
import|;
end_import

begin_class
DECL|class|DefaultGroupsFactory
specifier|public
class|class
name|DefaultGroupsFactory
block|{
DECL|field|ALL_ENTRIES_GROUP_DEFAULT_ICON
specifier|private
specifier|static
name|String
name|ALL_ENTRIES_GROUP_DEFAULT_ICON
init|=
literal|"ALL_ENTRIES_GROUP_ICON"
decl_stmt|;
DECL|method|DefaultGroupsFactory ()
specifier|private
name|DefaultGroupsFactory
parameter_list|()
block|{     }
DECL|method|getAllEntriesGroup ()
specifier|public
specifier|static
name|AllEntriesGroup
name|getAllEntriesGroup
parameter_list|()
block|{
name|AllEntriesGroup
name|group
init|=
operator|new
name|AllEntriesGroup
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"All entries"
argument_list|)
argument_list|)
decl_stmt|;
name|group
operator|.
name|setIconName
argument_list|(
name|ALL_ENTRIES_GROUP_DEFAULT_ICON
argument_list|)
expr_stmt|;
return|return
name|group
return|;
block|}
block|}
end_class

end_unit

