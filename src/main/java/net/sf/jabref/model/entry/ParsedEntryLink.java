begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|java
operator|.
name|util
operator|.
name|Optional
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
name|BibDatabase
import|;
end_import

begin_class
DECL|class|ParsedEntryLink
specifier|public
class|class
name|ParsedEntryLink
block|{
DECL|field|key
specifier|private
name|String
name|key
decl_stmt|;
DECL|field|linkedEntry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|linkedEntry
decl_stmt|;
DECL|field|dataBase
specifier|private
name|BibDatabase
name|dataBase
decl_stmt|;
DECL|method|ParsedEntryLink (String key, BibDatabase dataBase)
specifier|public
name|ParsedEntryLink
parameter_list|(
name|String
name|key
parameter_list|,
name|BibDatabase
name|dataBase
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|key
expr_stmt|;
name|this
operator|.
name|linkedEntry
operator|=
name|dataBase
operator|.
name|getEntryByKey
argument_list|(
name|this
operator|.
name|key
argument_list|)
expr_stmt|;
name|this
operator|.
name|dataBase
operator|=
name|dataBase
expr_stmt|;
block|}
DECL|method|ParsedEntryLink (BibEntry bibEntry)
specifier|public
name|ParsedEntryLink
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|bibEntry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|this
operator|.
name|linkedEntry
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|key
return|;
block|}
DECL|method|getLinkedEntry ()
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|getLinkedEntry
parameter_list|()
block|{
return|return
name|linkedEntry
return|;
block|}
DECL|method|setKey (String newKey)
specifier|public
name|void
name|setKey
parameter_list|(
name|String
name|newKey
parameter_list|)
block|{
name|this
operator|.
name|key
operator|=
name|newKey
expr_stmt|;
name|this
operator|.
name|linkedEntry
operator|=
name|getDataBase
argument_list|()
operator|.
name|getEntryByKey
argument_list|(
name|this
operator|.
name|key
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|key
argument_list|,
name|linkedEntry
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object obj)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|obj
operator|instanceof
name|ParsedEntryLink
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
name|ParsedEntryLink
name|other
init|=
operator|(
name|ParsedEntryLink
operator|)
name|obj
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|key
argument_list|,
name|other
operator|.
name|key
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|linkedEntry
argument_list|,
name|other
operator|.
name|linkedEntry
argument_list|)
return|;
block|}
DECL|method|getDataBase ()
specifier|public
name|BibDatabase
name|getDataBase
parameter_list|()
block|{
return|return
name|dataBase
return|;
block|}
block|}
end_class

end_unit
