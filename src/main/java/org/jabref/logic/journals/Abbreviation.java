begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
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
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_class
DECL|class|Abbreviation
specifier|public
class|class
name|Abbreviation
implements|implements
name|Comparable
argument_list|<
name|Abbreviation
argument_list|>
block|{
DECL|field|SPLITTER
specifier|private
specifier|static
specifier|final
name|String
name|SPLITTER
init|=
literal|";"
decl_stmt|;
comment|// elements after SPLITTER are not used at the moment
DECL|field|name
specifier|private
specifier|final
name|SimpleStringProperty
name|name
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|abbreviation
specifier|private
specifier|final
name|SimpleStringProperty
name|abbreviation
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|method|Abbreviation (String name, String abbreviation)
specifier|public
name|Abbreviation
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|abbreviation
parameter_list|)
block|{
name|this
operator|.
name|name
operator|.
name|set
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|name
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|abbreviation
operator|.
name|set
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|abbreviation
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setName (String name)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|.
name|set
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|nameProperty ()
specifier|public
name|SimpleStringProperty
name|nameProperty
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getAbbreviation ()
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|this
operator|.
name|abbreviation
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setAbbreviation (String abbreviation)
specifier|public
name|void
name|setAbbreviation
parameter_list|(
name|String
name|abbreviation
parameter_list|)
block|{
name|this
operator|.
name|abbreviation
operator|.
name|set
argument_list|(
name|abbreviation
argument_list|)
expr_stmt|;
block|}
DECL|method|abbreviationProperty ()
specifier|public
name|SimpleStringProperty
name|abbreviationProperty
parameter_list|()
block|{
return|return
name|abbreviation
return|;
block|}
DECL|method|getIsoAbbreviation ()
specifier|public
name|String
name|getIsoAbbreviation
parameter_list|()
block|{
if|if
condition|(
name|getAbbreviation
argument_list|()
operator|.
name|contains
argument_list|(
name|SPLITTER
argument_list|)
condition|)
block|{
name|String
index|[]
name|restParts
init|=
name|getAbbreviation
argument_list|()
operator|.
name|split
argument_list|(
name|SPLITTER
argument_list|)
decl_stmt|;
return|return
name|restParts
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
return|;
block|}
return|return
name|getAbbreviation
argument_list|()
return|;
block|}
DECL|method|getMedlineAbbreviation ()
specifier|public
name|String
name|getMedlineAbbreviation
parameter_list|()
block|{
return|return
name|getIsoAbbreviation
argument_list|()
operator|.
name|replace
argument_list|(
literal|"."
argument_list|,
literal|" "
argument_list|)
operator|.
name|replace
argument_list|(
literal|"  "
argument_list|,
literal|" "
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (Abbreviation toCompare)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Abbreviation
name|toCompare
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|toCompare
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getNext (String current)
specifier|public
name|String
name|getNext
parameter_list|(
name|String
name|current
parameter_list|)
block|{
name|String
name|currentTrimmed
init|=
name|current
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|getMedlineAbbreviation
argument_list|()
operator|.
name|equals
argument_list|(
name|currentTrimmed
argument_list|)
condition|)
block|{
return|return
name|getName
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|currentTrimmed
argument_list|)
condition|)
block|{
return|return
name|getIsoAbbreviation
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|getMedlineAbbreviation
argument_list|()
return|;
block|}
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
name|String
operator|.
name|format
argument_list|(
literal|"Abbreviation{name=%s, iso=%s, medline=%s}"
argument_list|,
name|getName
argument_list|()
argument_list|,
name|getIsoAbbreviation
argument_list|()
argument_list|,
name|getMedlineAbbreviation
argument_list|()
argument_list|)
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
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|o
operator|instanceof
name|Abbreviation
condition|)
block|{
name|Abbreviation
name|that
init|=
operator|(
name|Abbreviation
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|getName
argument_list|()
argument_list|,
name|that
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
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
return|return
name|Objects
operator|.
name|hashCode
argument_list|(
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

