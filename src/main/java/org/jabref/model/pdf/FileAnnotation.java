begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.pdf
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeParseException
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|RemoveNewlinesFormatter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|cos
operator|.
name|COSName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|interactive
operator|.
name|annotation
operator|.
name|PDAnnotation
import|;
end_import

begin_class
DECL|class|FileAnnotation
specifier|public
class|class
name|FileAnnotation
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|FileAnnotation
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|ABBREVIATED_ANNOTATION_NAME_LENGTH
specifier|private
specifier|final
specifier|static
name|int
name|ABBREVIATED_ANNOTATION_NAME_LENGTH
init|=
literal|45
decl_stmt|;
DECL|field|DATE_TIME_STRING
specifier|private
specifier|static
specifier|final
name|String
name|DATE_TIME_STRING
init|=
literal|"^D:\\d{14}$"
decl_stmt|;
DECL|field|DATE_TIME_STRING_WITH_TIME_ZONE
specifier|private
specifier|static
specifier|final
name|String
name|DATE_TIME_STRING_WITH_TIME_ZONE
init|=
literal|"^D:\\d{14}.+"
decl_stmt|;
DECL|field|ANNOTATION_DATE_FORMAT
specifier|private
specifier|static
specifier|final
name|String
name|ANNOTATION_DATE_FORMAT
init|=
literal|"yyyyMMddHHmmss"
decl_stmt|;
DECL|field|author
specifier|private
specifier|final
name|String
name|author
decl_stmt|;
DECL|field|timeModified
specifier|private
specifier|final
name|LocalDateTime
name|timeModified
decl_stmt|;
DECL|field|page
specifier|private
specifier|final
name|int
name|page
decl_stmt|;
DECL|field|content
specifier|private
specifier|final
name|String
name|content
decl_stmt|;
DECL|field|annotationType
specifier|private
specifier|final
name|FileAnnotationType
name|annotationType
decl_stmt|;
DECL|field|linkedFileAnnotation
specifier|private
specifier|final
name|Optional
argument_list|<
name|FileAnnotation
argument_list|>
name|linkedFileAnnotation
decl_stmt|;
comment|/**      * A flexible constructor, mainly used as dummy if there is actually no annotation.      *      * @param author         The authors of the annotation      * @param timeModified   The last time this annotation was modified      * @param pageNumber     The page of the pdf where the annotation occurs      * @param content        the actual content of the annotation      * @param annotationType the type of the annotation      */
DECL|method|FileAnnotation (final String author, final LocalDateTime timeModified, final int pageNumber, final String content, final FileAnnotationType annotationType, final Optional<FileAnnotation> linkedFileAnnotation)
specifier|public
name|FileAnnotation
parameter_list|(
specifier|final
name|String
name|author
parameter_list|,
specifier|final
name|LocalDateTime
name|timeModified
parameter_list|,
specifier|final
name|int
name|pageNumber
parameter_list|,
specifier|final
name|String
name|content
parameter_list|,
specifier|final
name|FileAnnotationType
name|annotationType
parameter_list|,
specifier|final
name|Optional
argument_list|<
name|FileAnnotation
argument_list|>
name|linkedFileAnnotation
parameter_list|)
block|{
name|this
operator|.
name|author
operator|=
name|author
expr_stmt|;
name|this
operator|.
name|timeModified
operator|=
name|timeModified
expr_stmt|;
name|this
operator|.
name|page
operator|=
name|pageNumber
expr_stmt|;
name|this
operator|.
name|content
operator|=
name|parseContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|this
operator|.
name|annotationType
operator|=
name|annotationType
expr_stmt|;
name|this
operator|.
name|linkedFileAnnotation
operator|=
name|linkedFileAnnotation
expr_stmt|;
block|}
comment|/**      * Creating a normal FileAnnotation from a PDAnnotation.      *      * @param annotation The actual annotation that holds the information      * @param pageNumber The page of the pdf where the annotation occurs      */
DECL|method|FileAnnotation (final PDAnnotation annotation, final int pageNumber)
specifier|public
name|FileAnnotation
parameter_list|(
specifier|final
name|PDAnnotation
name|annotation
parameter_list|,
specifier|final
name|int
name|pageNumber
parameter_list|)
block|{
name|this
argument_list|(
name|annotation
operator|.
name|getDictionary
argument_list|()
operator|.
name|getString
argument_list|(
name|COSName
operator|.
name|T
argument_list|)
argument_list|,
name|extractModifiedTime
argument_list|(
name|annotation
operator|.
name|getModifiedDate
argument_list|()
argument_list|)
argument_list|,
name|pageNumber
argument_list|,
name|annotation
operator|.
name|getContents
argument_list|()
argument_list|,
name|FileAnnotationType
operator|.
name|parse
argument_list|(
name|annotation
argument_list|)
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * For creating a FileAnnotation that has a connection to another FileAnnotation. Needed when creating a text      * highlighted or underlined annotation with a sticky note.      *      * @param annotation           The actual annotation that holds the information      * @param pageNumber           The page of the pdf where the annotation occurs      * @param linkedFileAnnotation The corresponding note of a marked text area.      */
DECL|method|FileAnnotation (final PDAnnotation annotation, final int pageNumber, FileAnnotation linkedFileAnnotation)
specifier|public
name|FileAnnotation
parameter_list|(
specifier|final
name|PDAnnotation
name|annotation
parameter_list|,
specifier|final
name|int
name|pageNumber
parameter_list|,
name|FileAnnotation
name|linkedFileAnnotation
parameter_list|)
block|{
name|this
argument_list|(
name|annotation
operator|.
name|getDictionary
argument_list|()
operator|.
name|getString
argument_list|(
name|COSName
operator|.
name|T
argument_list|)
argument_list|,
name|extractModifiedTime
argument_list|(
name|annotation
operator|.
name|getModifiedDate
argument_list|()
argument_list|)
argument_list|,
name|pageNumber
argument_list|,
name|annotation
operator|.
name|getContents
argument_list|()
argument_list|,
name|FileAnnotationType
operator|.
name|parse
argument_list|(
name|annotation
argument_list|)
argument_list|,
name|Optional
operator|.
name|of
argument_list|(
name|linkedFileAnnotation
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Parses a String into a LocalDateTime.      *      * @param dateTimeString In this case of format yyyyMMddHHmmss.      * @return a LocalDateTime parsed from the dateTimeString      */
DECL|method|extractModifiedTime (String dateTimeString)
specifier|public
specifier|static
name|LocalDateTime
name|extractModifiedTime
parameter_list|(
name|String
name|dateTimeString
parameter_list|)
block|{
if|if
condition|(
name|dateTimeString
operator|==
literal|null
condition|)
block|{
return|return
name|LocalDateTime
operator|.
name|now
argument_list|()
return|;
block|}
if|if
condition|(
name|dateTimeString
operator|.
name|matches
argument_list|(
name|DATE_TIME_STRING_WITH_TIME_ZONE
argument_list|)
condition|)
block|{
name|dateTimeString
operator|=
name|dateTimeString
operator|.
name|substring
argument_list|(
literal|2
argument_list|,
literal|16
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dateTimeString
operator|.
name|matches
argument_list|(
name|DATE_TIME_STRING
argument_list|)
condition|)
block|{
name|dateTimeString
operator|=
name|dateTimeString
operator|.
name|substring
argument_list|(
literal|2
argument_list|)
expr_stmt|;
block|}
try|try
block|{
return|return
name|LocalDateTime
operator|.
name|parse
argument_list|(
name|dateTimeString
argument_list|,
name|DateTimeFormatter
operator|.
name|ofPattern
argument_list|(
name|ANNOTATION_DATE_FORMAT
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|DateTimeParseException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Expected a parseable date string! However, this text could not be parsed: '%s'"
argument_list|,
name|dateTimeString
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|LocalDateTime
operator|.
name|now
argument_list|()
return|;
block|}
block|}
DECL|method|parseContent (final String content)
specifier|private
name|String
name|parseContent
parameter_list|(
specifier|final
name|String
name|content
parameter_list|)
block|{
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
specifier|final
name|String
name|unreadableContent
init|=
literal|"Ã¾Ã¿"
decl_stmt|;
if|if
condition|(
name|content
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|unreadableContent
argument_list|)
condition|)
block|{
return|return
literal|""
return|;
block|}
comment|// remove line breaks
name|String
name|processedContent
init|=
name|content
operator|.
name|replace
argument_list|(
literal|"\r\n"
argument_list|,
literal|" "
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|" "
argument_list|)
operator|.
name|replace
argument_list|(
literal|"\r"
argument_list|,
literal|" "
argument_list|)
decl_stmt|;
return|return
name|processedContent
operator|.
name|trim
argument_list|()
return|;
block|}
comment|/**      * Abbreviate annotation names when they are longer than {@code ABBREVIATED_ANNOTATION_NAME_LENGTH} chars      *      * @param annotationName annotation to be shortened      * @return the abbreviated name      */
DECL|method|abbreviateAnnotationName (final String annotationName)
specifier|private
name|String
name|abbreviateAnnotationName
parameter_list|(
specifier|final
name|String
name|annotationName
parameter_list|)
block|{
if|if
condition|(
name|annotationName
operator|.
name|length
argument_list|()
operator|>
name|ABBREVIATED_ANNOTATION_NAME_LENGTH
condition|)
block|{
return|return
name|annotationName
operator|.
name|subSequence
argument_list|(
literal|0
argument_list|,
name|ABBREVIATED_ANNOTATION_NAME_LENGTH
argument_list|)
operator|.
name|toString
argument_list|()
operator|+
literal|"..."
return|;
block|}
return|return
name|annotationName
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
name|abbreviateAnnotationName
argument_list|(
name|content
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object other)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|other
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|other
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
operator|(
name|other
operator|==
literal|null
operator|)
operator|||
operator|(
name|getClass
argument_list|()
operator|!=
name|other
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
name|FileAnnotation
name|that
init|=
operator|(
name|FileAnnotation
operator|)
name|other
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|annotationType
argument_list|,
name|that
operator|.
name|annotationType
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|author
argument_list|,
name|that
operator|.
name|author
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|content
argument_list|,
name|that
operator|.
name|content
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|page
argument_list|,
name|that
operator|.
name|page
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|linkedFileAnnotation
argument_list|,
name|that
operator|.
name|linkedFileAnnotation
argument_list|)
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|timeModified
argument_list|,
name|that
operator|.
name|timeModified
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
name|annotationType
argument_list|,
name|author
argument_list|,
name|content
argument_list|,
name|page
argument_list|,
name|linkedFileAnnotation
argument_list|,
name|timeModified
argument_list|)
return|;
block|}
DECL|method|getAuthor ()
specifier|public
name|String
name|getAuthor
parameter_list|()
block|{
return|return
name|author
return|;
block|}
DECL|method|getTimeModified ()
specifier|public
name|LocalDateTime
name|getTimeModified
parameter_list|()
block|{
return|return
name|timeModified
return|;
block|}
DECL|method|getPage ()
specifier|public
name|int
name|getPage
parameter_list|()
block|{
return|return
name|page
return|;
block|}
DECL|method|getContent ()
specifier|public
name|String
name|getContent
parameter_list|()
block|{
return|return
name|content
return|;
block|}
DECL|method|getAnnotationType ()
specifier|public
name|FileAnnotationType
name|getAnnotationType
parameter_list|()
block|{
return|return
name|annotationType
return|;
block|}
DECL|method|hasLinkedAnnotation ()
specifier|public
name|boolean
name|hasLinkedAnnotation
parameter_list|()
block|{
return|return
name|this
operator|.
name|linkedFileAnnotation
operator|.
name|isPresent
argument_list|()
return|;
block|}
comment|/**      * Before this getter is called the presence of the linked annotation must be checked via hasLinkedAnnotation()!      *      * @return the note attached to the annotation      */
DECL|method|getLinkedFileAnnotation ()
specifier|public
name|FileAnnotation
name|getLinkedFileAnnotation
parameter_list|()
block|{
return|return
name|linkedFileAnnotation
operator|.
name|get
argument_list|()
return|;
block|}
block|}
end_class

end_unit

