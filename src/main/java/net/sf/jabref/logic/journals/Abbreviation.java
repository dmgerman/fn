begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|Objects
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
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|abbreviation
specifier|private
specifier|final
name|String
name|abbreviation
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
operator|=
name|java
operator|.
name|util
operator|.
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|name
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
name|this
operator|.
name|abbreviation
operator|=
name|java
operator|.
name|util
operator|.
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|abbreviation
argument_list|)
operator|.
name|trim
argument_list|()
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
return|;
block|}
DECL|method|getIsoAbbreviation ()
specifier|public
name|String
name|getIsoAbbreviation
parameter_list|()
block|{
name|String
name|SPLITTER
init|=
literal|";"
decl_stmt|;
comment|// elements after SPLITTER are not used at the moment
if|if
condition|(
name|abbreviation
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
name|abbreviation
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
name|abbreviation
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
name|replaceAll
argument_list|(
literal|"\\."
argument_list|,
literal|" "
argument_list|)
operator|.
name|replaceAll
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
DECL|method|hasIsoAndMedlineAbbreviationsAreSame ()
specifier|public
name|boolean
name|hasIsoAndMedlineAbbreviationsAreSame
parameter_list|()
block|{
return|return
name|getIsoAbbreviation
argument_list|()
operator|.
name|equals
argument_list|(
name|getMedlineAbbreviation
argument_list|()
argument_list|)
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
name|name
operator|.
name|compareTo
argument_list|(
name|toCompare
operator|.
name|name
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
name|name
argument_list|,
name|getIsoAbbreviation
argument_list|()
argument_list|,
name|getMedlineAbbreviation
argument_list|()
argument_list|)
return|;
block|}
DECL|method|toPropertiesLine ()
specifier|public
name|String
name|toPropertiesLine
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s = %s"
argument_list|,
name|name
argument_list|,
name|getAbbreviation
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getAbbreviation ()
specifier|public
name|String
name|getAbbreviation
parameter_list|()
block|{
return|return
name|abbreviation
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
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
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
name|equal
argument_list|(
name|name
argument_list|,
name|that
operator|.
name|name
argument_list|)
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
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

