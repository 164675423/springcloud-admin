// 首页组件
import React from 'react';
import {Row, Col} from 'antd';
import PropTypes from 'prop-types';

import {productRoute, userRoute, orderRoute} from 'util/route.js';
import PageWrapper from 'component/page-wrapper';
import Card from './card.js';
import {G2, Chart, Geom, Axis, Tooltip, Coord, Label, Legend, View, Guide, Shape, Facet, Util} from 'bizcharts';
import DataSet from '@antv/data-set';
import {Histogram, lineChart, lineChart2, Histogram2} from './mock';

class Home extends React.PureComponent {
    componentDidMount() {
        document.title = '管理系统';
        this.props.getStatisticData();
        this.props.setCurrentPageCode();
    }

    render() {
        const routeData = [{
            key: '/',
            text: '首页'
        }];

        const {data, cols} = lineChart;
        const {data2, cols2} = lineChart2;

        const ds = new DataSet();
        const dv = ds.createView().source(Histogram);
        dv.transform({
            type: 'fold',
            fields: ['Jan.', 'Feb.', 'Mar.', 'Apr.', 'May', 'Jun.', 'Jul.', 'Aug.', 'Sep.', 'Oct.', 'Nov.', 'Dec.'],
            // 展开字段集
            key: '月份',
            // key字段
            value: '月均降雨量' // value字段
        });
        return (
            <div>
                <Row gutter={20}>
                    <Chart height={400} data={dv} forceFit>
                        <Axis name="月份" />
                        <Axis name="月均降雨量" />
                        <Legend />
                        <Tooltip
                            crosshairs={{
                                type: 'y'
                            }}/>
                        <Geom
                            type="interval"
                            position="月份*月均降雨量"
                            color={'name'}
                            adjust={[
                                {
                                    type: 'dodge',
                                    marginRatio: 1 / 32
                                }
                            ]}/>
                    </Chart>
                </Row>
                <Row gutter={10}>
                    <Col md={8}>
                        <Chart height={400} data={data} scale={cols} forceFit>
                            <Axis name="year" />
                            <Axis name="value" />
                            <Tooltip
                                crosshairs={{
                                    type: 'y'
                                }}/>
                            <Geom type="line" position="year*value" size={2} />
                            <Geom
                                type="point"
                                position="year*value"
                                size={4}
                                shape={'circle'}
                                style={{
                                    stroke: '#fff',
                                    lineWidth: 1
                                }}/>
                        </Chart>
                    </Col>
                    <Col md={8}>
                        <Chart height={400} data={data2} scale={cols2} forceFit>
                            <Axis
                                name="month"
                                title={null}
                                tickLine={null}
                                line={{
                                    stroke: '#E6E6E6'
                                }}/>
                            <Axis
                                name="acc"
                                line={false}
                                tickLine={null}
                                grid={null}
                                title={null}/>
                            <Tooltip />
                            <Geom
                                type="line"
                                position="month*acc"
                                size={1}
                                color="l (270) 0:rgba(255, 146, 255, 1) .5:rgba(100, 268, 255, 1) 1:rgba(215, 0, 255, 1)"
                                shape="smooth"
                                style={{
                                    shadowColor: 'l (270) 0:rgba(21, 146, 255, 0)',
                                    shadowBlur: 60,
                                    shadowOffsetY: 6
                                }}/>
                        </Chart>
                    </Col>
                    <Col md={8}>
                        <Chart
                            height={400}
                            data={Histogram2.HistogramData2}
                            scale={Histogram2.HistogramScale2}
                            padding="auto"
                            forceFit
                            onGetG2Instance={chart => {
                                setTimeout(() => {
                                    const geom = chart.get('geoms')[0];
                                    geom.setSelected(data[1]);
                                }, 1000);
                            }}
                            onPlotClick={ev => {
                                document.getElementById('container').append(`<p>${JSON.stringify(ev.data._origin)}</p>`);
                            }}>
                            <Axis name="sold" />
                            <Axis name="genre" />
                            <Geom
                                type="interval"
                                position="genre*sold"
                                color={['genre', '#E6F6C8-#3376CB']}
                                select={[
                                    true,
                                    {
                                        mode: 'single', // 选中模式，单选、多选
                                        style: {
                                            stroke: '#2C9D61',
                                            lineWidth: 3,
                                        }, // 选中后 shape 的样式
                                        cancelable: false, // 选中之后是否允许取消选中，默认允许取消选中
                                        animate: true, // 选中是否执行动画，默认执行动画
                                    },
                                ]}/>
                        </Chart>
                    </Col>
                </Row>
                
            </div>
        );
    }
}


export default Home;
