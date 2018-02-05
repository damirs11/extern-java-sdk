/*
 * Kontur.Extern.Api.Public
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: v1
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package ru.skbkontur.sdk.extern.rest.swagger.model;

import java.util.Objects;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.IOException;
import java.util.UUID;
import ru.skbkontur.sdk.extern.rest.swagger.model.SignatureToSend;

/**
 * DocumentToSend
 */

public class DocumentToSend {
  @SerializedName("id")
  private UUID id = null;

  @SerializedName("content")
  private byte[] content = null;

  @SerializedName("filename")
  private String filename = null;

  @SerializedName("signature")
  private SignatureToSend signature = null;

  @SerializedName("sender-ip")
  private String senderIp = null;

  public DocumentToSend id(UUID id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "00000000-0000-0000-0000-000000000000", value = "")
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public DocumentToSend content(byte[] content) {
    this.content = content;
    return this;
  }

   /**
   * Get content
   * @return content
  **/
  @ApiModelProperty(value = "")
  public byte[] getContent() {
    return content;
  }

  public void setContent(byte[] content) {
    this.content = content;
  }

  public DocumentToSend filename(String filename) {
    this.filename = filename;
    return this;
  }

   /**
   * Get filename
   * @return filename
  **/
  @ApiModelProperty(value = "")
  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public DocumentToSend signature(SignatureToSend signature) {
    this.signature = signature;
    return this;
  }

   /**
   * Get signature
   * @return signature
  **/
  @ApiModelProperty(value = "")
  public SignatureToSend getSignature() {
    return signature;
  }

  public void setSignature(SignatureToSend signature) {
    this.signature = signature;
  }

  public DocumentToSend senderIp(String senderIp) {
    this.senderIp = senderIp;
    return this;
  }

   /**
   * Get senderIp
   * @return senderIp
  **/
  @ApiModelProperty(value = "")
  public String getSenderIp() {
    return senderIp;
  }

  public void setSenderIp(String senderIp) {
    this.senderIp = senderIp;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentToSend documentToSend = (DocumentToSend) o;
    return Objects.equals(this.id, documentToSend.id) &&
        Objects.equals(this.content, documentToSend.content) &&
        Objects.equals(this.filename, documentToSend.filename) &&
        Objects.equals(this.signature, documentToSend.signature) &&
        Objects.equals(this.senderIp, documentToSend.senderIp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, content, filename, signature, senderIp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentToSend {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    senderIp: ").append(toIndentedString(senderIp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
  
}

