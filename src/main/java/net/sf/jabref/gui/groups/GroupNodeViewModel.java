begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
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
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|Bindings
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleIntegerProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|database
operator|.
name|BibDatabaseContext
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
name|event
operator|.
name|EntryEvent
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
name|groups
operator|.
name|AbstractGroup
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
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|GroupNodeViewModel
specifier|public
class|class
name|GroupNodeViewModel
block|{
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|isRoot
specifier|private
specifier|final
name|boolean
name|isRoot
decl_stmt|;
DECL|field|iconCode
specifier|private
specifier|final
name|String
name|iconCode
decl_stmt|;
DECL|field|children
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|children
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|groupNode
specifier|private
specifier|final
name|GroupTreeNode
name|groupNode
decl_stmt|;
DECL|field|hits
specifier|private
specifier|final
name|SimpleIntegerProperty
name|hits
decl_stmt|;
DECL|field|hasChildren
specifier|private
specifier|final
name|SimpleBooleanProperty
name|hasChildren
decl_stmt|;
DECL|method|GroupNodeViewModel (BibDatabaseContext databaseContext, GroupTreeNode groupNode)
specifier|public
name|GroupNodeViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|GroupTreeNode
name|groupNode
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|groupNode
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|groupNode
argument_list|)
expr_stmt|;
name|name
operator|=
name|groupNode
operator|.
name|getName
argument_list|()
expr_stmt|;
name|isRoot
operator|=
name|groupNode
operator|.
name|isRoot
argument_list|()
expr_stmt|;
name|iconCode
operator|=
literal|""
expr_stmt|;
name|children
operator|=
name|EasyBind
operator|.
name|map
argument_list|(
name|groupNode
operator|.
name|getChildren
argument_list|()
argument_list|,
name|child
lambda|->
operator|new
name|GroupNodeViewModel
argument_list|(
name|databaseContext
argument_list|,
name|child
argument_list|)
argument_list|)
expr_stmt|;
name|hasChildren
operator|=
operator|new
name|SimpleBooleanProperty
argument_list|()
expr_stmt|;
name|hasChildren
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|isNotEmpty
argument_list|(
name|children
argument_list|)
argument_list|)
expr_stmt|;
name|hits
operator|=
operator|new
name|SimpleIntegerProperty
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|calculateNumberOfMatches
argument_list|()
expr_stmt|;
comment|// Register listener
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|registerListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|GroupNodeViewModel (BibDatabaseContext databaseContext, AbstractGroup group)
specifier|public
name|GroupNodeViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|AbstractGroup
name|group
parameter_list|)
block|{
name|this
argument_list|(
name|databaseContext
argument_list|,
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllEntriesGroup (BibDatabaseContext newDatabase)
specifier|static
name|GroupNodeViewModel
name|getAllEntriesGroup
parameter_list|(
name|BibDatabaseContext
name|newDatabase
parameter_list|)
block|{
return|return
operator|new
name|GroupNodeViewModel
argument_list|(
name|newDatabase
argument_list|,
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
argument_list|)
return|;
block|}
DECL|method|hasChildrenProperty ()
specifier|public
name|SimpleBooleanProperty
name|hasChildrenProperty
parameter_list|()
block|{
return|return
name|hasChildren
return|;
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
DECL|method|isRoot ()
specifier|public
name|boolean
name|isRoot
parameter_list|()
block|{
return|return
name|isRoot
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
literal|"Some group named "
operator|+
name|getName
argument_list|()
return|;
block|}
DECL|method|getHits ()
specifier|public
name|SimpleIntegerProperty
name|getHits
parameter_list|()
block|{
return|return
name|hits
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|GroupNodeViewModel
name|that
init|=
operator|(
name|GroupNodeViewModel
operator|)
name|o
decl_stmt|;
if|if
condition|(
name|isRoot
operator|!=
name|that
operator|.
name|isRoot
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|name
operator|.
name|equals
argument_list|(
name|that
operator|.
name|name
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|iconCode
operator|.
name|equals
argument_list|(
name|that
operator|.
name|iconCode
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|children
operator|.
name|equals
argument_list|(
name|that
operator|.
name|children
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|databaseContext
operator|.
name|equals
argument_list|(
name|that
operator|.
name|databaseContext
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
operator|!
name|groupNode
operator|.
name|equals
argument_list|(
name|that
operator|.
name|groupNode
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
name|hits
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|that
operator|.
name|hits
operator|.
name|getValue
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"GroupNodeViewModel{"
operator|+
literal|"name='"
operator|+
name|name
operator|+
literal|'\''
operator|+
literal|", isRoot="
operator|+
name|isRoot
operator|+
literal|", iconCode='"
operator|+
name|iconCode
operator|+
literal|'\''
operator|+
literal|", children="
operator|+
name|children
operator|+
literal|", databaseContext="
operator|+
name|databaseContext
operator|+
literal|", groupNode="
operator|+
name|groupNode
operator|+
literal|", hits="
operator|+
name|hits
operator|+
literal|'}'
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
name|int
name|result
init|=
name|name
operator|.
name|hashCode
argument_list|()
decl_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
operator|(
name|isRoot
condition|?
literal|1
else|:
literal|0
operator|)
expr_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
name|iconCode
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
name|children
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
name|databaseContext
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
name|groupNode
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
literal|31
operator|*
name|result
operator|+
name|hits
operator|.
name|hashCode
argument_list|()
expr_stmt|;
return|return
name|result
return|;
block|}
DECL|method|getIconCode ()
specifier|public
name|String
name|getIconCode
parameter_list|()
block|{
return|return
name|iconCode
return|;
block|}
DECL|method|getChildren ()
specifier|public
name|ObservableList
argument_list|<
name|GroupNodeViewModel
argument_list|>
name|getChildren
parameter_list|()
block|{
return|return
name|children
return|;
block|}
DECL|method|getGroupNode ()
specifier|public
name|GroupTreeNode
name|getGroupNode
parameter_list|()
block|{
return|return
name|groupNode
return|;
block|}
comment|/**      * Gets invoked if an entry in the current database changes.      */
annotation|@
name|Subscribe
DECL|method|listen (@uppressWarningsR) EntryEvent entryEvent)
specifier|public
name|void
name|listen
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|EntryEvent
name|entryEvent
parameter_list|)
block|{
name|calculateNumberOfMatches
argument_list|()
expr_stmt|;
block|}
DECL|method|calculateNumberOfMatches ()
specifier|private
name|void
name|calculateNumberOfMatches
parameter_list|()
block|{
comment|// We calculate the new hit value
comment|// We could be more intelligent and try to figure out the new number of hits based on the entry change
comment|// for example, a previously matched entry gets removed -> hits = hits - 1
operator|new
name|Thread
argument_list|(
parameter_list|()
lambda|->
block|{
name|int
name|newHits
init|=
name|groupNode
operator|.
name|calculateNumberOfMatches
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
decl_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
name|hits
operator|.
name|setValue
argument_list|(
name|newHits
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|addSubgroup (AbstractGroup subgroup)
specifier|public
name|GroupTreeNode
name|addSubgroup
parameter_list|(
name|AbstractGroup
name|subgroup
parameter_list|)
block|{
return|return
name|groupNode
operator|.
name|addSubgroup
argument_list|(
name|subgroup
argument_list|)
return|;
block|}
block|}
end_class

end_unit

