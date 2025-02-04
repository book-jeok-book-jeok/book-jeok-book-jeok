import React, { Children, useEffect, useState } from "react";
import axios from "axios";
import styled from "styled-components";
import PostingRegisterPresenter from "./PostingRegisterPresenter";
import img_none from "../../../res/img/img_none.webp";
import img_discuss from "../../../res/img/img_discuss.jpg";
import img_seminar from "../../../res/img/img_seminar.jpg";
import img_study from "../../../res/img/img_study.jpg";
import img_free from "../../../res/img/img_free.jpg";

const url = "https://i6a305.p.ssafy.io:8443";

function PostingRegisterContainer() {
  const [title, setTitle] = useState();
  const [content, setContent] = useState();
  const [startDate, setStartDate] = useState();
  const [endDate, setEndDate] = useState();
  const [deadline, setDeadLine] = useState();
  const [maximum, setMaximum] = useState(3);
  const [days, setDays] = useState([]);
  const [limitLevel, setLimitLevel] = useState(1);
  const [readingGroupType, setReadingGroupType] = useState("none");
  const jwtToken = JSON.parse(sessionStorage.getItem("jwtToken"));
  let img;
  if (readingGroupType === "none") {
    img = img_none;
  } else if (readingGroupType === "discuss") {
    img = img_discuss;
  } else if (readingGroupType === "seminar") {
    img = img_seminar;
  } else if (readingGroupType === "study") {
    img = img_study;
  } else {
    img = img_free;
  }
  const onTypeButtonHandler = (event) => {
    setReadingGroupType(event.target.id);
  };
  const onTitleChange = (event) => {
    setTitle(event.target.value);
  };
  const onContentChange = (event) => {
    setContent(event.target.value);
  };
  const onStartDateChange = (event) => {
    setStartDate(event.target.value);
  };
  const onEndDateChange = (event) => {
    setEndDate(event.target.value);
  };
  const onDayChange = (event) => {
    if (event.target.checked) {
      setDays([...days, event.target.id]);
    } else {
      setDays(days.filter((day) => day !== event.target.id));
    }
  };
  const onMaximumChange = (event) => {
    setMaximum(event.target.value);
  };
  const onDeadLineChange = (event) => {
    setDeadLine(event.target.value);
  };
  const onLevelChange = (event) => {
    setLimitLevel(event.target.value);
  };
  const submitHandler = async () => {
    console.log({
      title,
      content,
      limitLevel: parseInt(limitLevel),
      maxMember: parseInt(maximum),
      deadline,
      startDate,
      endDate,
      readingGroupType,
      days,
    });

    if (deadline >= startDate) {
      alert("독서모임 시작일 혹은 모집 마감일을 다시 설정해주세요.");
    } else if (startDate + 6 > endDate) {
      alert("독서모임 기간을 다시 설정해주세요.");
    } else {
      try {
        const response = await axios.post(
          url + `/api/v1/reading-groups`,
          {
            title,
            content,
            limitLevel: parseInt(limitLevel),
            maxMember: parseInt(maximum),
            deadline,
            startDate,
            endDate,
            readingGroupType,
            days,
          },
          {
            headers: {
              Authorization: `Bearer ` + jwtToken,
            },
          }
        );
        console.log(response);
        if (response.data.status === 201) {
          alert(response.data.data.msg);
          document.location.href = "/postinglist";
        }
      } catch (e) {
        console.log(e);
        alert("입력 내용을 확인해주세요.");
      }

      // .then(function (response) {
      //   console.log(response);
      //   if (response.status === 200) {
      //     alert(response.data.data.msg);
      //     // document.location.href = "/postinglist";
      //   }
      //   if (response.status === 500) {
      //     alert("입력 내용을 확인해주세요.");
      //   }
      // });
    }
  };

  return (
    <PostingRegisterPresenter
      onTypeButtonHandler={onTypeButtonHandler}
      onTitleChange={onTitleChange}
      onContentChange={onContentChange}
      title={title}
      content={content}
      onStartDateChange={onStartDateChange}
      onEndDateChange={onEndDateChange}
      onDayChange={onDayChange}
      onMaximumChange={onMaximumChange}
      onDeadLineChange={onDeadLineChange}
      onLevelChange={onLevelChange}
      submitHandler={submitHandler}
      img={img}
    />
  );
}
export default PostingRegisterContainer;
