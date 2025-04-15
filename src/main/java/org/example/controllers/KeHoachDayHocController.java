package org.example.controllers;

import org.example.models.KeHoachDayHoc;
import org.example.services.KeHoachDayHocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/kehoachdayhoc")
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
public class KeHoachDayHocController {

    @Autowired
    private KeHoachDayHocService keHoachDayHocService;

    @PostMapping
    public ResponseEntity<KeHoachDayHoc> themKeHoach(@RequestBody KeHoachDayHoc keHoachDayHoc) {
        return ResponseEntity.ok(keHoachDayHocService.themKeHoach(keHoachDayHoc));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KeHoachDayHoc> capNhatKeHoach(@PathVariable Integer id, @RequestBody KeHoachDayHoc keHoachDayHoc) {
        return ResponseEntity.ok(keHoachDayHocService.capNhatKeHoach(id, keHoachDayHoc));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoaKeHoach(@PathVariable Integer id) {
        keHoachDayHocService.xoaKeHoach(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<KeHoachDayHoc>> layTatCaKeHoach() {
        List<KeHoachDayHoc> keHoachList = keHoachDayHocService.layTatCaKeHoach();
        return ResponseEntity.ok(keHoachList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeHoachDayHoc> layKeHoachTheoId(@PathVariable Integer id) {
        return ResponseEntity.ok(keHoachDayHocService.layKeHoachTheoId(id));
    }

    @GetMapping("/hocky/{hocKy}")
    public ResponseEntity<List<KeHoachDayHoc>> layKeHoachTheoHocKy(@PathVariable Integer hocKy) {
        return ResponseEntity.ok(keHoachDayHocService.layKeHoachTheoHocKy(hocKy));
    }

    @GetMapping("/chuyennganh/{idChuyenNganh}/hocky/{hocKy}")
    public ResponseEntity<List<KeHoachDayHoc>> layKeHoachTheoChuyenNganhVaHocKy(
            @PathVariable Integer idChuyenNganh,
            @PathVariable Integer hocKy) {
        return ResponseEntity.ok(keHoachDayHocService.layKeHoachTheoChuyenNganhVaHocKy(idChuyenNganh, hocKy));
    }

    @GetMapping("/hocphan/{hocPhanId}/hocky/{hocKy}")
    public ResponseEntity<List<KeHoachDayHoc>> layKeHoachTheoHocPhanVaHocKy(
            @PathVariable Integer hocPhanId,
            @PathVariable Integer hocKy) {
        return ResponseEntity.ok(keHoachDayHocService.layKeHoachTheoHocPhanVaHocKy(hocPhanId, hocKy));
    }

    @PostMapping("/{id}/hocphan")
    public ResponseEntity<Void> themHocPhanVaoKeHoach(
            @PathVariable Integer id,
            @RequestBody Map<String, Integer> request) {
        Integer idHocPhan = request.get("idHocPhan");
        keHoachDayHocService.themHocPhanVaoKeHoach(id, idHocPhan);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/hocphan/{idHocPhan}")
    public ResponseEntity<Void> xoaHocPhanKhoiKeHoach(
            @PathVariable Integer id,
            @PathVariable Integer idHocPhan) {
        keHoachDayHocService.xoaHocPhanKhoiKeHoach(id, idHocPhan);
        return ResponseEntity.ok().build();
    }
}