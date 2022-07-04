import {View, Text, TouchableOpacity, Platform} from 'react-native';
import React, {useState} from 'react';
import {useDispatch, useSelector} from 'react-redux';
import {backgroundTaskCheck, increment} from '../slice/counter';
import ThreadModule from '../utils/ThreadModule';

export default function TaskTest() {
  const taskCheck = useSelector(state => state.counter.backgroundTask);
  const count = useSelector(state => state.counter.value);

  const dispatch = useDispatch();

  const moduleTestBtn = () => {
    //잘 작동 모듈 연결 O
    ThreadModule.show('hi', 3000);
  };

  const backgroundTaskStartBtn = () => {
    if (Platform.OS === 'android') {
      console.log('네이티브에서 클릭');
      //버튼을 클릭했을 때 forground service 실행
      //task가 실행중이지 않을 경우에만 설정
      if (!taskCheck) {
        ThreadModule.startService();
        dispatch(backgroundTaskCheck(true));
      }
      console.log(taskCheck);
    }
  };

  const backgroundTaskStopBtn = () => {
    if (taskCheck) {
      ThreadModule.stopService();
      dispatch(backgroundTaskCheck(false));
    }
  };

  const WorkerManagerStartBtn = () => {
    if (taskCheck) {
      ThreadModule.startService();
      dispatch(backgroundTaskCheck(true));
    }
  };

  const WorkerManagerStopBtn = () => {
    if (taskCheck) {
      ThreadModule.stopService();
      dispatch(backgroundTaskCheck(false));
    }
  };
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>TaskTest</Text>
      <TouchableOpacity
        style={{padding: 10, backgroundColor: 'orange'}}
        onPress={moduleTestBtn}>
        <Text>모듈 테스트</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={{padding: 10, backgroundColor: 'orange'}}
        onPress={backgroundTaskStartBtn}>
        <Text>백그라운드 실행</Text>
      </TouchableOpacity>
      <Text style={{fontSize: 60, color: '#000'}}>{count}</Text>
      <TouchableOpacity
        style={{padding: 10, backgroundColor: 'green'}}
        onPress={backgroundTaskStopBtn}>
        <Text style={{color: '#FFF'}}>STOP</Text>
      </TouchableOpacity>

      <TouchableOpacity
        style={{padding: 10, backgroundColor: 'orange'}}
        onPress={WorkerManagerStartBtn}>
        <Text>WorkerManager 실행</Text>
      </TouchableOpacity>
      <TouchableOpacity
        style={{padding: 10, backgroundColor: 'orange'}}
        onPress={WorkerManagerStopBtn}>
        <Text>WorkerManager 종료</Text>
      </TouchableOpacity>
    </View>
  );
}
