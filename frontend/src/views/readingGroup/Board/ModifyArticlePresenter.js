import React, { useState, useEffect, useReducer } from "react";
import styled from "styled-components";
const Block = styled.div`
  height: 100vh;
`;
const Article = styled.div`
  position: relative;
  width: 100%;
  height: 100%;
  border: 1px solid black;
  border-radius: 20px;
`;
const TitleBlock = styled.div`
  margin-top: 5%;
  position: relative;
  height: 5%;
  width: 80%;
  margin-left: 10%;
  text-align: left;
`;
const Title = styled.input`
  position: relative;
  margin-left: 3%;
  width: 90%;
  height: 100%;
`;
const ContentBlock = styled.div`
  margin-top: 3%;
  position: relative;
  height: 30%;
  width: 80%;
  margin-left: 10%;
  text-align: left;
`;
const Content = styled.input`
  position: relative;
  margin-left: 3%;
  width: 90%;
  height: 100%;
`;
const Image = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 3%;
`;
const SubmitBtn = styled.button`
  position: relative;
  height: 5%;
  width: 10%;
  border: 1px;
  background: black;
  color: white;
  border-radius: 20px;
  margin-top: 1%;
`;
const Preview = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 1%;
`;

const Message = styled.div`
  position: relative;
  text-align: left;
  margin-left: 15%;
  margin-top: 1%;
`;

function ModifyArticlePresenter({
  handleTitle,
  handleContent,
  handleFiles,
  title,
  content,
  files,
  onCreate,
}) {
  return (
    <Block>
      <Article>
        <TitleBlock>
          <label>제목</label>
          <Title onChange={handleTitle} value={title}></Title>
        </TitleBlock>
        <ContentBlock>
          <label>내용</label>
          <Content onChange={handleContent} value={content}></Content>
        </ContentBlock>
        <Image>
          {/* <input
            id="upload-file"
            type="file"
            accept="image/*, video/*"
            multiple
            onChange={uploadFile}
          ></input> */}
        </Image>
        <Message>
          사진을 여러개 선택하고 싶을 경우 한번에 선택해주세요.(최대 5장)
        </Message>
        <Preview>
          {/* {postfiles.file !== null ? (
            <img src={postfiles.previewURL} width="300px" height="200px" />
          ) : null} */}
        </Preview>
        <SubmitBtn onClick={onCreate}>작성 완료</SubmitBtn>
      </Article>
    </Block>
  );
}
export default ModifyArticlePresenter;
