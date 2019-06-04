begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|texparser
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_class
DECL|class|Citation
specifier|public
class|class
name|Citation
block|{
comment|/**      * The total number of characters that are shown around a cite (cite width included).      */
DECL|field|CONTEXT_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|CONTEXT_WIDTH
init|=
literal|50
decl_stmt|;
DECL|field|path
specifier|private
specifier|final
name|Path
name|path
decl_stmt|;
DECL|field|line
specifier|private
specifier|final
name|int
name|line
decl_stmt|;
DECL|field|colStart
specifier|private
specifier|final
name|int
name|colStart
decl_stmt|;
DECL|field|colEnd
specifier|private
specifier|final
name|int
name|colEnd
decl_stmt|;
DECL|field|lineText
specifier|private
specifier|final
name|String
name|lineText
decl_stmt|;
DECL|method|Citation (Path path, int line, int colStart, int colEnd, String lineText)
specifier|public
name|Citation
parameter_list|(
name|Path
name|path
parameter_list|,
name|int
name|line
parameter_list|,
name|int
name|colStart
parameter_list|,
name|int
name|colEnd
parameter_list|,
name|String
name|lineText
parameter_list|)
block|{
name|this
operator|.
name|path
operator|=
name|path
expr_stmt|;
name|this
operator|.
name|line
operator|=
name|line
expr_stmt|;
name|this
operator|.
name|colStart
operator|=
name|colStart
expr_stmt|;
name|this
operator|.
name|colEnd
operator|=
name|colEnd
expr_stmt|;
name|this
operator|.
name|lineText
operator|=
name|lineText
expr_stmt|;
block|}
DECL|method|getPath ()
specifier|public
name|Path
name|getPath
parameter_list|()
block|{
return|return
name|path
return|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
DECL|method|getColStart ()
specifier|public
name|int
name|getColStart
parameter_list|()
block|{
return|return
name|colStart
return|;
block|}
DECL|method|getColEnd ()
specifier|public
name|int
name|getColEnd
parameter_list|()
block|{
return|return
name|colEnd
return|;
block|}
DECL|method|getLineText ()
specifier|public
name|String
name|getLineText
parameter_list|()
block|{
return|return
name|lineText
return|;
block|}
comment|/**      * Get a fixed-width string that shows the context of a citation.      *      * @return String that contains a cite and the text that surrounds it along the same line.      */
DECL|method|getContext ()
specifier|public
name|String
name|getContext
parameter_list|()
block|{
name|int
name|center
init|=
operator|(
name|colStart
operator|+
name|colEnd
operator|)
operator|/
literal|2
decl_stmt|;
name|int
name|lineLength
init|=
name|lineText
operator|.
name|length
argument_list|()
decl_stmt|;
name|int
name|start
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
operator|(
name|center
operator|+
name|CONTEXT_WIDTH
operator|/
literal|2
operator|<
name|lineLength
operator|)
condition|?
name|center
operator|-
name|CONTEXT_WIDTH
operator|/
literal|2
else|:
name|lineLength
operator|-
name|CONTEXT_WIDTH
argument_list|)
decl_stmt|;
name|int
name|end
init|=
name|Math
operator|.
name|min
argument_list|(
name|lineLength
argument_list|,
name|start
operator|+
name|CONTEXT_WIDTH
argument_list|)
decl_stmt|;
return|return
name|lineText
operator|.
name|substring
argument_list|(
name|start
argument_list|,
name|end
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
name|String
operator|.
name|format
argument_list|(
literal|"%s (%d:%d-%d) \"%s\""
argument_list|,
name|path
argument_list|,
name|line
argument_list|,
name|colStart
argument_list|,
name|colEnd
argument_list|,
name|getContext
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
block|{
return|return
literal|false
return|;
block|}
name|Citation
name|citation
init|=
operator|(
name|Citation
operator|)
name|o
decl_stmt|;
return|return
name|path
operator|.
name|equals
argument_list|(
name|citation
operator|.
name|path
argument_list|)
operator|&&
name|line
operator|==
name|citation
operator|.
name|line
operator|&&
name|colStart
operator|==
name|citation
operator|.
name|colStart
operator|&&
name|colEnd
operator|==
name|citation
operator|.
name|colEnd
operator|&&
name|lineText
operator|.
name|equals
argument_list|(
name|citation
operator|.
name|lineText
argument_list|)
operator|&&
name|getContext
argument_list|()
operator|.
name|equals
argument_list|(
name|citation
operator|.
name|getContext
argument_list|()
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
name|hash
argument_list|(
name|path
argument_list|,
name|line
argument_list|,
name|colStart
argument_list|,
name|colEnd
argument_list|,
name|lineText
argument_list|)
return|;
block|}
block|}
end_class

end_unit

